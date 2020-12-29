package com.study.review.exception;

public class MyException extends RuntimeException{
	public MyException(String msg) {
		super(msg);
	}
	
	public MyException(String msg, Throwable e) {
		super(msg, e);
	}
}
