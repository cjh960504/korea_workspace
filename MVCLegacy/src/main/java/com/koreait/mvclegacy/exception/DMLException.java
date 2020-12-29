package com.koreait.mvclegacy.exception;

//MemberRegistException, BoardEditException... 다양하게 만들 수 있음 잘 활용해보자
public class DMLException extends RuntimeException{
	public DMLException(String msg) {
		super(msg);
	}
	
	public DMLException(String msg, Throwable e) {
		super(msg, e);
	}
}
