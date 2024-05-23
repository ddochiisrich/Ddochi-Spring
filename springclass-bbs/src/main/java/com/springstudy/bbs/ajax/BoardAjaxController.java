package com.springstudy.bbs.ajax;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.springstudy.bbs.domain.Reply;
import com.springstudy.bbs.service.BoardService;

@Controller
public class BoardAjaxController {

	@Autowired
	private BoardService boardService;
	
	@RequestMapping("replyDelete.ajax")
	@ResponseBody
	public  List<Reply> deleteReply(int no, int bbsNo) {
		
		boardService.deleteReply(no);
		
		return boardService.replyList(bbsNo);
		
	}
	
	@RequestMapping("replyUpdate.ajax")
	@ResponseBody
	public List<Reply> updateReply(Reply reply) {

		boardService.updateReply(reply);
		
		return boardService.replyList(reply.getBbsNo());
	}
	
	// 댓글
	@RequestMapping("/replyWrite.ajax")
	@ResponseBody
	public List<Reply> addReply(Reply reply){
		
		// 댓글을 추가
		boardService.addReply(reply);
		
		// 갱신된 댓글 리스트를 읽어와서 반환		
		return boardService.replyList(reply.getBbsNo());
	}

	@RequestMapping("recommend.ajax")
	@ResponseBody
	public Map<String, Integer>recommend(int no, String recommend) {
		
		
		return boardService.recommend(no, recommend);
	}
	
}
