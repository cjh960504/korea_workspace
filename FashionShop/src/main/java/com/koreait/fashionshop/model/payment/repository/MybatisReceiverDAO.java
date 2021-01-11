package com.koreait.fashionshop.model.payment.repository;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.koreait.fashionshop.exception.ReceiverException;
import com.koreait.fashionshop.model.domain.Receiver;

@Repository
public class MybatisReceiverDAO implements ReceiverDAO{
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	@Override
	public void insert(Receiver receiver) throws ReceiverException{
		int result = sqlSessionTemplate.insert("Receiver.insert", receiver);
		result = 0; //일부로 테스트목적
		if(result==0) {
			throw new ReceiverException("배송 정보 등록 오류!");
		}
	}
}
