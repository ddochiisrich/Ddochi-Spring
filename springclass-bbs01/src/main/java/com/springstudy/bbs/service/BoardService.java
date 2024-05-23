package com.springstudy.bbs.service;

import java.util.List;
import java.util.Map;

import com.springstudy.bbs.domain.Board;
import com.springstudy.bbs.domain.Reply;

public interface BoardService {
	
	public abstract void deleteReply(int no);
	
	public abstract void addReply(Reply reply);
	
	public abstract void updateReply(Reply reply);
	
	public abstract Map<String, Integer> recommend(int no, String recommend);
	
	public abstract List<Reply> replyList(int no);

	public abstract Board getBoard(int no, boolean isCount);
	
	public abstract void insertBoard(Board board);
	
	public boolean isPassCheck(int no, String pass);
	
	public abstract void updateBoard(Board board);
	
	public abstract void deleteBoard(int no);
	
	public abstract Map<String, Object> boardList(int pageNum, String type, String keyword);
}
