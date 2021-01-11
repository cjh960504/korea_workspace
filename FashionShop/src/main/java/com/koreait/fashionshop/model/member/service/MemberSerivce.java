package com.koreait.fashionshop.model.member.service;

import java.util.List;

import com.koreait.fashionshop.model.domain.Member;

public interface MemberSerivce {
	public List selectAll();
	public Member select(Member member);
	public void regist(Member member);//회원등록 및 기타 필요사항 처리...
	//서비스의 존재는 DB에 insert하는 것만 하는게 아니다 그러므로 regist라는 이름으로 구분
	public void update(Member member);
	public void delete(Member member);//회원을 삭제한다는 것은 예민한 작업
}
