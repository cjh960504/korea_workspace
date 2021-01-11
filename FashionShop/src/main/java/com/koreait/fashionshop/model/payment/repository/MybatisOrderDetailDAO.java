package com.koreait.fashionshop.model.payment.repository;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.koreait.fashionshop.exception.OrderDetailException;
import com.koreait.fashionshop.model.domain.OrderDetail;

@Repository
public class MybatisOrderDetailDAO implements OrderDetailDAO{
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	@Override
	public void insert(OrderDetail orderDetail) throws OrderDetailException {
		int result = sqlSessionTemplate.insert("OrderDetail.insert", orderDetail);
		if(result==0) {
			throw new OrderDetailException("주문 상세정보 등록 오류");
		}
	}
}
