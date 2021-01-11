package com.koreait.fashionshop.model.payment.service;

import java.util.List;

import com.koreait.fashionshop.model.domain.Cart;
import com.koreait.fashionshop.model.domain.Member;
import com.koreait.fashionshop.model.domain.OrderDetail;
import com.koreait.fashionshop.model.domain.OrderSummary;
import com.koreait.fashionshop.model.domain.Receiver;

public interface PaymentService {
	//장바구니 관련 업무
	public List selectCartList();//회원구분없이 모든 내역 가져오기
	public List selectCartList(int member_id);//특정 회원의 장바구니 내역
	public Cart selectCart(int cart_id);
	public void insert(Cart cart);
	public void update(List<Cart> cart);
	public void delete(Cart cart);//회원id에 속한 데이터를 삭제할 예정
	public void delete(Member member);//회원id에 속한 데이터를 삭제할 예정
	
	//결제 관련 업무
	public List selectPaymethodList();
	
	//여기서 많은 트랜잭션 처리가 요구된다..(주문, 주문상세, 배송정보..)
	public void registOrder(OrderSummary orderSummary, Receiver receiver, OrderDetail orderDetail);
}
