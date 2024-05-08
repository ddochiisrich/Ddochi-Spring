package com.springclass.ch04.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.springclass.ch04.service.FirstMvcService;

@Controller
@RequestMapping("/first")
public class FirstMvcController {
	@Autowired
	private FirstMvcService service;

	public void setService(FirstMvcService service) {
		this.service = service;
	}
	
	@RequestMapping({"/", "/intro", "/index"})
	public String index() {
		return "/main";
	}
	
	@RequestMapping("/detail")
	public String detail(Model model, HttpServletRequest request, @RequestParam(value="num", defaultValue="1") int no, String id) {
		String msg = service.getMessage(no, id);
		model.addAttribute("msg", msg);
		
		model.addAttribute("title", "명준이의 디테일 당부");
		model.addAttribute("comment", "정말 루~ 스프링 공부 열심히 해야 해여~ %_%");
		
		return "detail";
	}
}
