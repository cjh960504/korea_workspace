package com.koreait.fashionshop.exception;

public class WidthdrawFailException extends RuntimeException{
	public WidthdrawFailException(String msg) {
		super(msg);
	}
		
	public WidthdrawFailException(String msg, Throwable e) {
		super(msg, e);
	}
}