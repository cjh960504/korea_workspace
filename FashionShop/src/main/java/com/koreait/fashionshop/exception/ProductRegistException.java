package com.koreait.fashionshop.exception;

//CRUD작업 시 발생될 수 있는 예외
public class ProductRegistException extends RuntimeException{
	public ProductRegistException(String msg) {
		super(msg);
	}
	
	public ProductRegistException(String msg, Throwable e) {
		super(msg, e);
	}
	
}
