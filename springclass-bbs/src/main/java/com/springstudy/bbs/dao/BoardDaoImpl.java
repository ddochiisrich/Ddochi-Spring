package com.springstudy.bbs.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.springstudy.bbs.domain.Board;
import com.springstudy.bbs.domain.Reply;

@Repository
public class BoardDaoImpl implements BoardDao {
	
	private final String NAME_SPACE ="com.springstudy.bbs.mapper.BoardMapper";
	
	@Autowired
	private SqlSessionTemplate sqlSession;

	@Override
	public List<Board> boardList(int startRow, int num, String type, String keyword) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("startRow", startRow);
		params.put("num" ,num);
		params.put("type", type);
		params.put("keyword", keyword);
		return sqlSession.selectList(NAME_SPACE + ".boardList", params);
	}

	@Override
	public Board getBoard(int no) {
		return sqlSession.selectOne(NAME_SPACE + ".getBoard", no);
	}

	@Override
	public void insertBoard(Board board) {
		sqlSession.insert(NAME_SPACE + ".insertBoard", board);
	}

	@Override
	public String isPassCheck(int no, String pass) {
		return  sqlSession.selectOne(NAME_SPACE +".isPassCheck",no);
	}

	@Override
	public void updateBoard(Board board) {
		sqlSession.update(NAME_SPACE +".updateBoard", board);
	}

	@Override
	public void deleteBoard(int no) {
		sqlSession.delete(NAME_SPACE + ".deleteBoard", no);
	}

	@Override
	public int getBoardCount(String type, String keyword) {
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("type", type);
		params.put("keyword", keyword);
		
		return sqlSession.selectOne(NAME_SPACE + ".getBoardCount", params);
	}

	@Override
	public void incrementReadCount(int no) {
		sqlSession.update(NAME_SPACE + ".incrementReadCount", no);
		
	}

	@Override
	public List<Reply> replyList(int no) {
		
		return sqlSession.selectList(NAME_SPACE + ".replyList", no);
	}

	@Override
	public void updateRecomemd(int no, String recommend) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("no", no);
		params.put("recommend", recommend);
		
		sqlSession.update(NAME_SPACE + ".updateRecommend", params);
		
	}

	@Override
	public Board getRecommend(int no) {
		
		return sqlSession.selectOne(NAME_SPACE + ".getRecommend", no);
	}

	@Override
	public void addReply(Reply reply) {
		sqlSession.insert(NAME_SPACE + ".addReply", reply);
		
	}

}
