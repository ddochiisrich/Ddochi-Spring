package com.springstudy.bbs.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springstudy.bbs.domain.Board;
import com.springstudy.bbs.domain.Reply;
import com.springstudy.bbs.service.BoardService;

@Controller
public class BoardController {

	private static final String DEFAULT_PATH = "resources/upload/";
	
	// BoardService 주입
	@Autowired
	private BoardService boardService;
	
	// 파일 다운로드 요청을 처리하는 메서드
	@GetMapping("/fileDownload")
	public void download(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String fileName = request.getParameter("fileName");
		System.out.println("fileName : " + fileName);
		
		String filePath = request.getServletContext().getRealPath(DEFAULT_PATH);
		
		File file = new File(filePath, fileName);
		
		response.setContentType("application/download; charset=UTF-8");
		response.setContentLength((int) file.length());
		
		fileName = URLEncoder.encode(file.getName(), "UTF-8");
		System.out.println("다운로드 fileName : + fileName");
		
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
		response.setHeader("Content-Transfer-Encoding", "binary");
		OutputStream out = response.getOutputStream();
		FileInputStream fis = new FileInputStream(file);
		
		FileCopyUtils.copy(fis, out);
		if(fis != null) {
			fis.close();
		}
		out.flush();
	}
	
	// 게시글 삭제
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public String delete(HttpServletResponse response, PrintWriter out, int no, String pass, RedirectAttributes reAttrs, @RequestParam(value="pageNum", defaultValue="1")int pageNum, @RequestParam(value="type", defaultValue="null")String type, @RequestParam(value="keyword", defaultValue="null")String keyword) {
		
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
		if(!(type.equals("null") || keyword.equals("null"))) {
			
			reAttrs.addAttribute("type", type);
			reAttrs.addAttribute("keyword", keyword);
			
		}
		
		return "redirect:boardList";
	}
	
	@PostMapping("/updateProcess")
	public String updateProcess(HttpServletResponse response, PrintWriter out, Board board, RedirectAttributes reAttrs, @RequestParam(value="pageNum", defaultValue="1")int pageNum, @RequestParam(value="type", defaultValue="null")String type, @RequestParam(value="keyword", defaultValue="null")String keyword) {
		
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
		
		boolean searchOption = (type.equals("null") || keyword.equals("null")) ? false : true;
		if(searchOption) {
			reAttrs.addAttribute("type", type);
			reAttrs.addAttribute("keyword", keyword);
		}
		
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
	public String insertBoard(HttpServletRequest request, String title, String writer, String content, String pass, @RequestParam (value="file1", required=false) MultipartFile multipartFile) throws IOException {
		
		System.out.println("originName : " + multipartFile.getOriginalFilename());
		System.out.println("name : " + multipartFile.getName());
		
		Board board = new Board();
		board.setTitle(title);
		board.setWriter(writer);
		board.setContent(content);
		board.setPass(pass);
		
		if(!multipartFile.isEmpty()) {// 파일이 업로드 되었으면
		
			String realPath = request.getServletContext().getRealPath(DEFAULT_PATH);
			
			UUID uid = UUID.randomUUID();
			String saveName = uid.toString() + "_" + multipartFile.getOriginalFilename();
			File file = new File(realPath, saveName);
			System.out.print("insertBoard - newName : " + file.getName());
			
			multipartFile.transferTo(file);
			board.setFile1(saveName);
		}
	
		boardService.insertBoard(board);
		
		return "redirect:boardList";
	}
	
	//게시 글 상세보기 요청을 처리하는 메서드
	@RequestMapping("/boardDetail")
	public String boardDetail(Model model, int no, @RequestParam(value="pageNum", defaultValue="1")int pageNum, @RequestParam(value="type", defaultValue="null")String type, @RequestParam(value="keyword", defaultValue="null")String keyword) {
		
		Board board = boardService.getBoard(no, true);
		model.addAttribute("board", board);
		model.addAttribute("pageNum", pageNum);
		
		List<Reply> replyList = boardService.replyList(no);
		model.addAttribute("replyList", replyList);
		
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
