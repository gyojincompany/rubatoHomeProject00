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
	public String board_list(Model model) {
		
		IDao dao = sqlSession.getMapper(IDao.class);
		ArrayList<FBoardDto> fboardDtos = dao.fblistDao();
		
		model.addAttribute("fblist", fboardDtos);
		
		return "board_list";
	}
	
	@RequestMapping(value = "/board_view")
	public String board_view() {
		
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
