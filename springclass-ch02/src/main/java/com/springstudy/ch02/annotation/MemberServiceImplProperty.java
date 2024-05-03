package com.springstudy.ch02.annotation;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springstudy.ch02.dao.MemberDAO;
import com.springstudy.ch02.domain.Member;
import com.springstudy.ch02.service.MemberService;

@Service
public class MemberServiceImplProperty implements MemberService{

	// DB 작업을 누구한테 의존 = MemberDAO
	private MemberDAO memberDAO;
	
	// MemberDAO 타입을 생성자를 통해서 주입받음
	@Autowired
	public void setMemberDAO(MemberDAO memberDAO) {
		this.memberDAO = memberDAO;
	}
	
	
	@Override
	public ArrayList<Member> getMemberList() {
		System.out.println("프로퍼티 주업");
		// 페이징, 검색관련 추가 처리 코드 추가
		return memberDAO.getMemberList();
	}

}
