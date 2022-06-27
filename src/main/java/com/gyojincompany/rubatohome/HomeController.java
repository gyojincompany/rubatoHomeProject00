package com.gyojincompany.rubatohome;

import java.io.File;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.gyojincompany.rubatohome.dao.IDao;
import com.gyojincompany.rubatohome.dto.FBoardDto;

@Controller
public class HomeController {
	
	@Autowired
	private SqlSession sqlSession;
	
	@RequestMapping(value="/index")
	public String index(Model model) {
		
		IDao dao = sqlSession.getMapper(IDao.class);
		
		ArrayList<FBoardDto> fboardDtos = dao.fblistDao();		
		
		model.addAttribute("freeboard01", fboardDtos.get(0));
		model.addAttribute("freeboard02", fboardDtos.get(1));
		model.addAttribute("freeboard03", fboardDtos.get(2));
		model.addAttribute("freeboard04", fboardDtos.get(3));		
		
		return "index";
	}
	
	@RequestMapping(value = "/board_list")
	public String board_list(HttpServletRequest request, Model model) {
		
		String searchKeyword = request.getParameter("searchKeyword");
		String searchOption = request.getParameter("searchOption");
		
		//System.out.println(searchKeyword);
		
		IDao dao = sqlSession.getMapper(IDao.class);
		
		ArrayList<FBoardDto> fboardDtos = null;
		
		if(searchOption == null || searchKeyword == null) {
			fboardDtos = dao.fblistDao();
		} else {
			if(searchOption.equals("title")) {
				//제목에서 특정 키워드 검색한 결과
				fboardDtos = dao.fbTitleSearchlistDao(searchKeyword);
			} else if(searchOption.equals("content") ) {
				//내용에서 특정 키워드 검색한 결과
				fboardDtos = dao.fbContentSearchlistDao(searchKeyword);
			} else {
				//글쓴이에서 특정 키워드 검색한 결과
				fboardDtos = dao.fbWriterSearchlistDao(searchKeyword);
			}
			
		}
		
		model.addAttribute("fblist", fboardDtos);
		model.addAttribute("listcount", fboardDtos.size());		
		
		
		return "board_list";
	}
	
	@RequestMapping(value = "/board_view")
	public String board_view(HttpServletRequest request, Model model) {
		
		IDao dao = sqlSession.getMapper(IDao.class);
		
		String fbnum = request.getParameter("fbnum");
		dao.fbhitDao(fbnum);//조회수 증가 함수
		
		model.addAttribute("fbview", dao.fbviewDao(fbnum));//게시글 내용
		model.addAttribute("fileInfo", dao.fbfileInfoDao(fbnum));//첨부파일
		model.addAttribute("rblist", dao.rblist(fbnum));//덧글 리스트
		
		return "board_view";
	}
	
	@RequestMapping(value = "/board_write")
	public String board_write() {
		
		return "board_write";
	}
	
	@RequestMapping(value = "/loginOk", method = RequestMethod.POST)
	public String loginOk(HttpServletRequest request, Model model) {
		
		String mid = request.getParameter("mid");
		String mpw = request.getParameter("mpw");
				
		IDao dao = sqlSession.getMapper(IDao.class);
		
		int checkIdFlag = dao.checkIdDao(mid);//아이디 존재 여부 체크 1이 반환되면 존재, 0이면 부존재
		int checkIdPwFlag = dao.checkIdPwDao(mid, mpw);//아이디와 비밀번호가 모두 일치하는 데이터 존재시 1이 반환
		
		if (checkIdPwFlag == 1) {
			HttpSession session = request.getSession();
			
			session.setAttribute("id", request.getParameter("mid"));
			
			model.addAttribute("memberId", request.getParameter("mid"));
		}
		
		return "redirect:index";
	}
	
	@RequestMapping(value = "/logout")
	public String logout() {
		
		return "logout";
	}
	
	@RequestMapping(value = "/fbWrite", method = RequestMethod.POST)
	public String fbWrite(HttpServletRequest request, @RequestPart MultipartFile files) throws Exception {
		
		IDao dao = sqlSession.getMapper(IDao.class);
		
		String fbname = request.getParameter("fbname");
		String fbtitle = request.getParameter("fbtitle");
		String fbcontent = request.getParameter("fbcontent");
		
		HttpSession session = request.getSession();
		String fbid = (String)session.getAttribute("id");
		
		if (fbid == null) {
			
			fbid = "GUEST";
		}
		
		if(files.isEmpty()) { //사용자가 게시글 작성시 파일 첨부 여부 판단
			//첨부된 파일이 없을 경우 사용자가 작성한 글만 DB에 삽입
			dao.fbwriteDao(fbname, fbtitle, fbcontent, fbid);
		} else { //사용자가 게시글에 파일을 첨부한 경우
			dao.fbwriteDao(fbname, fbtitle, fbcontent, fbid);
			
			String oriFileName = files.getOriginalFilename();//업로드된 파일의 원래 이름
			String oriFileNameExtension = FilenameUtils.getExtension(oriFileName).toLowerCase();//업로드된 파일의 확장자 추출(소문자로 변환)
			File destinationFile;//java.io 패키지의 클래스 임포트
			String destinationFileName;//실제 서버에 저장되는 파일의 이름 선언
			String fileUrl = "D:/spirngBoot_workspace/rubatoHomeProject00/src/main/resources/static/uploadfiles/";
			//첨부된 파일이 저장될 실제 폴더 경로
			
			do {
				destinationFileName = RandomStringUtils.randomAlphanumeric(32) + "." + oriFileNameExtension;
				//영대소문자와 숫자가 혼합된 랜덤 32글자 문자열 생성 후 원본 파일의 확장자 연결하여 실제 저장될 파일의 이름 생성
				destinationFile = new File(fileUrl+destinationFileName);
			} while(destinationFile.exists());//같은 이름의 파일이 저장소에 존재할 경우 다시 파일이름 생성
			
			destinationFile.getParentFile().mkdir();
			files.transferTo(destinationFile);
			
			ArrayList<FBoardDto> fbDtos = dao.fblistDao();
			
			int fbnum = fbDtos.get(0).getFbnum();//가져온 게시글 리스트 중에서 가장 최근글 불러온 후 getter로 fbnum 만 추출
			
			dao.fbfileInsertDao(fbnum, destinationFileName, oriFileName, fileUrl, oriFileNameExtension);
			
		}
		
		return "redirect:board_list";
	}
	
	@RequestMapping(value = "delete")
	public String delete(HttpServletRequest request) {
		
		String fbnum = request.getParameter("fbnum");//삭제할 게시판 글 번호 가져오기
		
		IDao dao = sqlSession.getMapper(IDao.class);
		
		dao.fbdeleteDao(fbnum);
		
		return "redirect:board_list";
	}
	
	@RequestMapping(value = "join")
	public String join() {
		
		return "member_join";
	}
	
	@RequestMapping(value = "joinOk", method = RequestMethod.POST)
	public String joinOk(HttpServletRequest request) {
		
		String mid = request.getParameter("memberid");
		String mpw = request.getParameter("memberpw");
		String mname = request.getParameter("membername");
		String memail = request.getParameter("memberemail");
		
		IDao dao = sqlSession.getMapper(IDao.class);
		
		dao.memberjoinDao(mid, mpw, mname, memail);
		
		HttpSession session = request.getSession();
		
		session.setAttribute("id", mid);
		session.setAttribute("name", mname);
		
		
		return "redirect:index";
	}
	
	@RequestMapping(value = "replyOk")
	public String replyOk(HttpServletRequest request, Model model) {
		
		String replycontent = request.getParameter("replycontent");
		String rborifbnum = request.getParameter("fbnum");
		int fbnum = Integer.parseInt(rborifbnum);
		
		HttpSession session = request.getSession();		
		String sessionId = (String)session.getAttribute("id");
		
		String rbid;
		
		if (sessionId == null) {
			rbid = "Guest";
		} else {
			rbid = sessionId;
		}
		
		IDao dao = sqlSession.getMapper(IDao.class);
		
		dao.rbwrite(fbnum, rbid, replycontent);
		
		dao.rbreplycount(rborifbnum);//덧글 수 1 증가
		
		model.addAttribute("fbview", dao.fbviewDao(rborifbnum));//게시글 내용
		model.addAttribute("fileInfo", dao.fbfileInfoDao(rborifbnum));//첨부파일
		model.addAttribute("rblist", dao.rblist(rborifbnum));//덧글 리스트
		
		return "board_view";
	}
	
	
	
	
	
	
}
