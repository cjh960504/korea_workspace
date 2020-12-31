package com.koreait.mvclegacy.model.notice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.koreait.mvclegacy.exception.DMLException;
import com.koreait.mvclegacy.model.domain.Notice;

@Service
public class NoticeService {
	//주입시키려하는 자료형이 2개이상일 경우, 개발자는 무엇을 사용할지 원하는 객체를 명시해야한다..
	//bean을 context에 등록하지 않고 component-scan을 하고 자동생성을 설정한 경우 id는 맨 첫글자만 소문자인 
	//클래스 이름이 id로 지정된다.(디폴트로)
	@Autowired
	@Qualifier("jdbcNoticeDAO")
	private NoticeDAO noticeDAO; //Mybatis전용, 그냥 팬이 아니라 Fripan
	//DI를 이용하여 외부파일로부터 의존성을 주입받을떄는 쉽게 코드를 바꿀 수 있게 클래스들의 공통상위객체로 선언
	
	//CRUD Method
	public List selectAll() {
		List list = noticeDAO.selectAll();
		return list;
	}
	
	public Notice select(int notice_id) {
		Notice notice = noticeDAO.select(notice_id);
		return notice;
	}
	
	//int가 없어진 이유? Exception으로 처리할거라서!!!
	public void insert(Notice notice) throws DMLException{
		noticeDAO.insert(notice);
	}
	
	public void update(Notice notice) throws DMLException {
		noticeDAO.update(notice);
	}
	
	public void delete(int notice_id) throws DMLException{
		noticeDAO.delete(notice_id);
	}
	
}
