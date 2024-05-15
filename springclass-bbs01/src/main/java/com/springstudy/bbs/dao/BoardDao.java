package com.springstudy.bbs.dao;

import java.util.List;

import com.springstudy.bbs.domain.Board;

public interface BoardDao {
	public abstract void incrementReadCount(int no);
	
	public abstract List<Board> boardList(int startRow, int num, String type, String keyword);
	
	public abstract int getBoardCount(String type, String keyword);
	
	public abstract Board getBoard(int no);
	
	public abstract void insertBoard(Board board);
	
	public String isPassCheck(int no, String pass);
	
	public abstract void updateBoard(Board board);
	
	public abstract void deleteBoard(int no);
}