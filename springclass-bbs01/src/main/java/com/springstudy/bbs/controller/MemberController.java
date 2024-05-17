package com.springstudy.bbs.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.springstudy.bbs.domain.Member;
import com.springstudy.bbs.service.MemberService;

@Controller
@SessionAttributes("member")
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	
	@PostMapping("/login")
	public String login(@RequestParam("userId") String id, String pass, HttpSession session, HttpServletResponse response, Model model) throws IOException{
		int result = memberService.login(id, pass);
		if(result == -1) {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('존재하지 않는 아이디 입니다.');");
			out.println("history.back();");
			out.println("</script>");
			
			return null;
		}else if(result == 0) {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('비밀번호가 틀립니다..');");
			out.println("history.back();");
			out.println("</script>");
			
			return null;
		}
		Member member = memberService.getMember(id);
		session.setAttribute("isLogin", true);
		model.addAttribute("member", member);
		
		return "redirect:boardList";
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		
		return "redirect:boardList";
	}

}
