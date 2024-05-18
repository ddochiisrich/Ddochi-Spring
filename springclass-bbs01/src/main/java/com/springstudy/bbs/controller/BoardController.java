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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springstudy.bbs.domain.Board;
import com.springstudy.bbs.service.BoardService;

@Controller
public class BoardController {

	private static final String DEFAULT_PATH = "/resources/upload";
	
	@Autowired
	private BoardService boardService;
	
	@RequestMapping("/fileDownload")
	public void download(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		String fileName = request.getParameter("fileName");
		
		System.out.println("fileName : " + fileName);
		
		String filePath = request.getServletContext().getRealPath(DEFAULT_PATH);
		
		File file = new File(filePath, fileName);
		System.out.println("file.getName() : " + file.getName());
		
		response.setContentType("application/download; charset=UTF-8");
		response.setContentLength((int)file.length());
		
		fileName = URLEncoder.encode(file.getName(), "UTF-8");
		System.out.println("다운로드 fileName : " + fileName);
		
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
		
		response.setHeader("Content-Transfer-Encoding", "binary");
		
		OutputStream out = response.getOutputStream();
		FileInputStream fis = null;
		
		fis = new FileInputStream(file);
		FileCopyUtils.copy(fis, out);
		
		if(fis != null) {
			fis.close();
		}
		out.flush();
	}
	
	@PostMapping("/deleteProcess")
	public String boardDelete(HttpServletResponse response, PrintWriter out, int no, String pass, RedirectAttributes reAttrs, @RequestParam(value="pageNum", defaultValue="1")int pageNum, @RequestParam(value="type", defaultValue="null") String type, @RequestParam(value="keyword", defaultValue="null") String keyword) {
		boolean result = boardService.isPassCheck(no, pass);
		
		if(!result) {
			response.setContentType("text/html; charset=utf-8");
			out.println("<script>");
			out.println(" alert('비밀번호가 맞지 않습니다.');");
			out.println(" history.back();");
			out.println("</script>");
			
			return null;
		}
		
		boolean searchOption = (type.equals("null") || keyword.equals("null")) ? false : true;
		
		boardService.deleteBoard(no);
		
		if(searchOption) {
			reAttrs.addAttribute("type", type);
			reAttrs.addAttribute("keyword", keyword);
		}
		
		reAttrs.addAttribute("pageNum" ,pageNum);
		
		return "redirect:boardList";
	}
	
	@RequestMapping(value={"/boardList", "/list"}, method=RequestMethod.GET)
	public String boardList(Model model, @RequestParam(value="pageNum", defaultValue="1") int pageNum, @RequestParam(value="type", defaultValue="null") String type, @RequestParam(value="keyword", defaultValue="null") String keyword) {
		Map<String, Object> modelMap = boardService.boardList(pageNum, type, keyword);
		model.addAllAttributes(modelMap);
		return "boardList";
	}
	
	@RequestMapping("/boardDetail")
	public String boardDetail(Model model, int no, @RequestParam(value="pageNum", defaultValue="1")int pageNum, @RequestParam(value="type", defaultValue="null")String type, @RequestParam(value="keyword", defaultValue="null")String keyword) {
		
		boolean searchOption = (type.equals("null") || keyword.equals("null")) ? false : true;
		
		Board board = boardService.getBoard(no, true);
		model.addAttribute("board", board);
		model.addAttribute("pageNum", pageNum);
		model.addAttribute("searchOption", searchOption);
		
		if(searchOption) {
			model.addAttribute("type", type);
			model.addAttribute("keyword", keyword);
			
		}
		return "boardDetail";
	}
	
	@RequestMapping(value="/writeProcess", method=RequestMethod.POST)
	public String insertBoard(HttpServletRequest request, String title, String writer, String content, String pass, @RequestParam(value="file1", required=false) MultipartFile multipartFile) throws IOException {
		
		System.out.println("originName : " + multipartFile.getOriginalFilename());
		System.out.println("name : " + multipartFile.getName());
		
		Board board = new Board();
		board.setTitle(title);
		board.setWriter(writer);
		board.setContent(content);
		board.setPass(pass);
		
		if(!multipartFile.isEmpty()) {
			String filePath = request.getServletContext().getRealPath(DEFAULT_PATH);
			
			UUID uid = UUID.randomUUID();
			String saveName = uid.toString() + "_" + multipartFile.getOriginalFilename();
			
			File file = new File(filePath, saveName);
			System.out.println("insertBoard - newName : " + file.getName());
			
			multipartFile.transferTo(file);
			board.setFile1(saveName);
		}
		
		
		boardService.insertBoard(board);
		
		return "redirect:boardList";
	}
	
	@RequestMapping(value="/update")
	public String updateBoard(Model model, HttpServletResponse response, PrintWriter out, int no, String pass, @RequestParam(value="pageNum", defaultValue="1")int pageNum, @RequestParam(value="type", defaultValue="null") String type, @RequestParam(value="keyword", defaultValue="null") String keyword) {
		boolean result = boardService.isPassCheck(no, pass);
		
		boolean searchOption = (type.equals("null") || keyword.equals("null")) ? false : true;
		
		if(!result) {
			response.setContentType("text/html; charset=UTF-8");
			out.println("<script>");
			out.println("alert('비밀번호가 맞지않습니다');");
			out.println("history.back();");
			out.println("</script>");
			
			return null;
		}
		Board board = boardService.getBoard(no, false);
		
		model.addAttribute("board", board);
		model.addAttribute("pageNum", pageNum);
		model.addAttribute("searchOption", searchOption);
		
		if(searchOption) {
			model.addAttribute("type", type);
			model.addAttribute("keyword", keyword);
		}
		
		return "updateForm";
	}
	
	@RequestMapping(value="/updateProcess", method=RequestMethod.POST)
	public String updateBoard(HttpServletResponse response, PrintWriter out, Board board, RedirectAttributes reAttrs, @RequestParam(value="pageNum", defaultValue="1")int pageNum, @RequestParam(value="type", defaultValue="null") String type, @RequestParam(value="keyword", defaultValue="null") String keyword) {
		boolean result = boardService.isPassCheck(board.getNo(), board.getPass());
		
		if(!result) {
			response.setContentType("text/html; charset=UTF-8");
			out.println("<script>");
			out.println("alert('비밀번호가 맞지않습니다');");
			out.println("history.back();");
			out.println("</script>");
			
			return null;
		}
		
		boolean searchOption = (type.equals("null") || keyword.equals("null")) ? false : true;
		
		boardService.updateBoard(board);
		
		if(searchOption) {
			reAttrs.addAttribute("type", type);
			reAttrs.addAttribute("keyword", keyword);
		}
		
		
		reAttrs.addAttribute("pageNum", pageNum);
		
		
		return "redirect:boardList";
	}
}
