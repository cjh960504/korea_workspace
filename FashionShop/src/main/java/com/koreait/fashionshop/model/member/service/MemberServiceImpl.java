package com.koreait.fashionshop.model.member.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.koreait.fashionshop.common.MailSender;
import com.koreait.fashionshop.common.SecureManager;
import com.koreait.fashionshop.exception.MailSendException;
import com.koreait.fashionshop.exception.MemberRegistException;
import com.koreait.fashionshop.model.domain.Member;
import com.koreait.fashionshop.model.member.repository.MemberDAO;

@Service
public class MemberServiceImpl implements MemberSerivce{
	@Autowired
	private MemberDAO memberDAO;
	
	//암호화객체
	@Autowired
    private SecureManager secureManager; 
	
	//이메일 발송 객체
	@Autowired
	private MailSender mailSender;
	
	@Override
	public List selectAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Member select(int member_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void regist(Member member) throws MemberRegistException, MailSendException{
		//DB에 넣기 + 이메일보내기 + 문자발송..
		member.setPassword(secureManager.getSecureData(member.getPassword()));
		memberDAO.insert(member);
		String name = member.getName();
		String email = member.getEmail_id()+"@"+member.getEmail_server();
		mailSender.send(email, "환영합니다! "+name+"님 Fashion Shop의 가입을 축하드립니다.", "<h1>즐거운 쇼핑되세요!~ 감사합니다!</h1>");
	}

	@Override
	public void update(Member member) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Member member) {
		// TODO Auto-generated method stub
		
	}

}
