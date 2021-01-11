package com.koreait.fashionshop.model.bank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.koreait.fashionshop.exception.DepositFailException;
import com.koreait.fashionshop.exception.WidthdrawFailException;
import com.koreait.fashionshop.model.bank.repository.KbankDAO;
import com.koreait.fashionshop.model.bank.repository.SbankDAO;

@Service
public class BankServiceImpl implements BankService{
	@Autowired
	private KbankDAO kbankDAO;
	
	@Autowired
	private SbankDAO sbankDAO;
	
	//송금(출금+입금)
	public void send(int money) throws WidthdrawFailException, DepositFailException {
		//세부업무가 2개인 트랜잭션
		kbankDAO.withdraw(money);//궁민은행으로부터 출금
		sbankDAO.deposit(money);//신한은행으로 입금
	}
}
