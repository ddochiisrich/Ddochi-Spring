package com.springstudy.bbs.service;

import com.springstudy.bbs.domain.Member;

public interface MemberService {

	public int login(String id, String pass);
	
	public Member getMember(String id);
	
}
