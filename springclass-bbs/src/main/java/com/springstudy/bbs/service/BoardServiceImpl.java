package com.springstudy.bbs.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springstudy.bbs.dao.BoardDao;
import com.springstudy.bbs.domain.Board;
import com.springstudy.bbs.domain.Reply;

@Service
public class BoardServiceImpl implements BoardService {
	
	//한페이지에 보여질 게시글의 수
	private final static int PAGE_SIZE = 10;
	
	// 한페이지에 보여질 페이지네이션의 수 - 페이지 그룹
	private final static int PAGE_GROUP = 10;

	// BoardDao 주입
	@Autowired
	private BoardDao boardDao;
	@Override
	public Map<String, Object> boardList(int pageNum, String type, String keyword) {
		
		int currentPage = pageNum;
		
		int startRow = (currentPage - 1) * PAGE_SIZE ;
		
		List<Board> bList = boardDao.boardList(startRow, PAGE_SIZE, type, keyword);
		int listCount = boardDao.getBoardCount(type, keyword);
		int pageCount = listCount / PAGE_SIZE + (listCount % PAGE_SIZE == 0 ? 0 : 1);
		int startPage = currentPage / PAGE_GROUP * PAGE_GROUP + 1 - (currentPage % PAGE_GROUP == 0 ? PAGE_GROUP : 0);
		int endPage = startPage + PAGE_GROUP - 1;
		
		if(endPage > pageCount) {
			endPage = pageCount;
		}
		Map<String, Object> modelMap = new HashMap<String, Object>();
		modelMap.put("bList", bList);
		modelMap.put("pageCount", pageCount);
		modelMap.put("startPage", startPage);
		modelMap.put("endPage", endPage);
		modelMap.put("currentPage", currentPage);
		modelMap.put("listCount", listCount);
		modelMap.put("pageGroup", PAGE_GROUP);
		
		boolean searchOption = (type.equals("null") || keyword.equals("null")) ? false : true;	
		modelMap.put("searchOption", searchOption);
		if(searchOption) {
			modelMap.put("type", type);
			modelMap.put("keyword", keyword);
		}
		
		
		return modelMap;
	}

	@Override
	public Board getBoard(int no, boolean isCount) {
		
		if(isCount) {// 게시글 읽은횟수 증가
			boardDao.incrementReadCount(no);
		}
		
		return boardDao.getBoard(no);
	}

	@Override
	public void insertBoard(Board board) {
		boardDao.insertBoard(board);
		
	}

	@Override
	public boolean isPassCheck(int no, String pass) {
		boolean result = false;
		String dbPass = boardDao.isPassCheck(no, pass);
		if(dbPass.equals(pass)) {
			result = true;
		}
		return result;
	}

	@Override
	public void updateBoard(Board board) {
		
		boardDao.updateBoard(board);
	}

	@Override
	public void deleteBoard(int no) {
		
		boardDao.deleteBoard(no);
		
	}

	@Override
	public List<Reply> replyList(int no) {
		return boardDao.replyList(no);
	}

	@Override
	public Map<String, Integer> recommend(int no, String recommend) {
		
		boardDao.updateRecomemd(no, recommend);
		Board board = boardDao.getBoard(no);
		
		Map<String, Integer> map = new HashMap<String, Integer>();
		
		map.put("recommend", board.getRecommend());
		map.put("thank", board.getThank());
		
		return map;
	}

	@Override
	public void addReply(Reply reply) {
		
		boardDao.addReply(reply);
		
	}

	@Override
	public void updateReply(Reply reply) {
		
		boardDao.updateReply(reply);
		
	}

	@Override
	public void deleteReply(int no) {
		boardDao.deleteReply(no);
		
	}

	

}
