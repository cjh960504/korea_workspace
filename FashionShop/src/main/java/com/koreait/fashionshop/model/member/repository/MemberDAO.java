package com.koreait.fashionshop.model.member.repository;

import java.util.List;

import com.koreait.fashionshop.model.domain.Member;

public interface MemberDAO {
	public List selectAll();
	public Member select(Member member);
	public void insert(Member member);
	public void update(Member member);
	public void delete(Member member);//회원을 삭제한다는 것은 예민한 작업
}
