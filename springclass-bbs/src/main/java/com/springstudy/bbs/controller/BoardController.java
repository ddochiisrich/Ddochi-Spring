package com.springstudy.bbs.controller;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springstudy.bbs.domain.Board;
import com.springstudy.bbs.service.BoardService;

@Controller
public class BoardController {

	// BoardService 주입
	@Autowired
	private BoardService boardService;
	
	// 게시글 삭제
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public String delete(HttpServletResponse response, PrintWriter out, int no, String pass, RedirectAttributes reAttrs, @RequestParam(value="pageNum", defaultValue="1")int pageNum) {
		
		boolean result = boardService.isPassCheck(no, pass);
		
		if(!result) { // 비밀번호가 일치하지않는경우
			response.setContentType("text/html; charset=UTF-8");
			out.println("<script>");
			out.println("alert('비밀번호가 일치하지 않습니다.');");
			out.println("history.back();");
			out.println("</script>");
			
			return null;
		}
		
		boardService.deleteBoard(no);
		
		reAttrs.addAttribute("pageNum", pageNum);
		return "redirect:boardList";
	}
	
	@PostMapping("/updateProcess")
	public String updateProcess(HttpServletResponse response, PrintWriter out, Board board, RedirectAttributes reAttrs, @RequestParam(value="pageNum", defaultValue="1")int pageNum) {
		
		boolean result = boardService.isPassCheck(board.getNo(), board.getPass());
		
		if(!result) { // 비밀번호가 일치하지않는경우
			response.setContentType("text/html; charset=UTF-8");
			out.println("<script>");
			out.println("alert('비밀번호가 일치하지 않습니다.');");
			out.println("history.back();");
			out.println("</script>");
			
			return null;
		}
		boardService.updateBoard(board);
		
		//RedirectAttribute
		reAttrs.addAttribute("pageNum", pageNum);
		reAttrs.addFlashAttribute("test", "1회성 파라미터");
		
		//return "redirect:boardList?pageNum=" + pageNum;	
		return "redirect:boardList";
	}
	
	@PostMapping("/update")
	public String updateBoard(Model model, HttpServletResponse response, PrintWriter out, int no, String pass, @RequestParam(value="pageNum", defaultValue="1")int pageNum, @RequestParam(value="type", defaultValue="null")String type, @RequestParam(value="keyword", defaultValue="null")String keyword) {
		
		boolean result = boardService.isPassCheck(no, pass);
		if(!result) { // 비밀번호가 일치하지않는경우
			response.setContentType("text/html; charset=UTF-8");
			out.println("<script>");
			out.println("alert('비밀번호가 일치하지 않습니다.');");
			out.println("history.back();");
			out.println("</script>");
			
			return null;
		}
		
		Board board = boardService.getBoard(no, false);
		model.addAttribute("board", board);
		model.addAttribute("pageNum", pageNum);
		
		boolean searchOption = (type.equals("null") || keyword.equals("null")) ? false : true;
		
		model.addAttribute("searchOption", searchOption);
		if(searchOption) {
			model.addAttribute("type", type);
			model.addAttribute("keyword", keyword);
		}
		
		return "updateForm";
	}
	
	// 게시글 쓰기 폼에서 들어오는 게시글 쓰기 요청을 처리하는 메서드
	//@RequestMapping(value="/writeProcess", method=RequestMethod.POST)
	@PostMapping("/writeProcess")
	public String insertBoard(Board board) {
		boardService.insertBoard(board);
		
		return "redirect:boardList";
	}
	
	//게시 글 상세보기 요청을 처리하는 메서드
	@RequestMapping("/boardDetail")
	public String boardDetail(Model model, int no, @RequestParam(value="pageNum", defaultValue="1")int pageNum, @RequestParam(value="type", defaultValue="null")String type, @RequestParam(value="keyword", defaultValue="null")String keyword) {
		
		Board board = boardService.getBoard(no, true);
		model.addAttribute("board", board);
		model.addAttribute("pageNum", pageNum);
		
		boolean searchOption = (type.equals("null") || keyword.equals("null")) ? false : true;
		model.addAttribute("searchOption", searchOption);
		if(searchOption) {
			model.addAttribute("type", type);
			model.addAttribute("keyword", keyword);
		}
		
		return "boardDetail";
	}
	
	// 게시글 리스트 요청을 처리하는 메서드
	//@RequestMapping(value={"/boardList", "list"}, method=RequestMethod.GET)
	@GetMapping({"/boardList", "list"})
	public String boardList(Model model,@ModelAttribute("test") String test, 
			@RequestParam(value="pageNum", defaultValue="1")int pageNum, 
			@RequestParam(value="type", defaultValue="null")String type, 
			@RequestParam(value="keyword", defaultValue="null")String keyword) {
		
		Map<String, Object> modelMap = boardService.boardList(pageNum, type, keyword);
		model.addAllAttributes(modelMap);

		
		// viewResolver에 설정되어 있는 prefix, suffix를 적용해서 뷰가 만들어진다.
		// /WEB-INF/views/ + boardList + .jsp
		return "boardList";
	}
	
}
