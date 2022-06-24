package com.gyojincompany.rubatohome;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gyojincompany.rubatohome.dao.IDao;
import com.gyojincompany.rubatohome.dto.FBoardDto;

@Controller
public class HomeController {
	
	@Autowired
	private SqlSession sqlSession;
	
	@RequestMapping(value="/index")
	public String index() {
		
		return "index";
	}
	
	@RequestMapping(value = "/board_list")
	public String board_list(HttpServletRequest request, Model model) {
		
		String searchKeyword = request.getParameter("searchKeyword");
		String searchOption = request.getParameter("searchOption");
		
		System.out.println(searchKeyword);
		
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
		
		model.addAttribute("fbview", dao.fbviewDao(fbnum));		
		
		return "board_view";
	}
	
	@RequestMapping(value = "/board_write")
	public String board_write() {
		
		return "board_write";
	}
	
	@RequestMapping(value = "/loginOk", method = RequestMethod.POST)
	public String joinOk(HttpServletRequest request, Model model) {
		
		HttpSession session = request.getSession();
		
		session.setAttribute("id", request.getParameter("mid"));
		
		model.addAttribute("memberId", request.getParameter("mid"));
		
		return "redirect:index";
	}
	
	@RequestMapping(value = "/logout")
	public String logout() {
		
		return "logout";
	}
	
	@RequestMapping(value = "/fbWrite")
	public String fbWrite(HttpServletRequest request) {
		
		IDao dao = sqlSession.getMapper(IDao.class);
		
		String fbname = request.getParameter("fbname");
		String fbtitle = request.getParameter("fbtitle");
		String fbcontent = request.getParameter("fbcontent");
		
		HttpSession session = request.getSession();
		String fbid = (String)session.getAttribute("id");
		
		if (fbid == null) {
			
			fbid = "GUEST";
		}
		
		dao.fbwriteDao(fbname, fbtitle, fbcontent, fbid);
		
		
		
		return "redirect:board_list";
	}
	
	
}
