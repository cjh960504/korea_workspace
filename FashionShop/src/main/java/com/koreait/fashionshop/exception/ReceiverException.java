package com.koreait.fashionshop.exception;

public class ReceiverException extends RuntimeException{
	public ReceiverException(String msg) {
		super(msg);
	}
		
	public ReceiverException(String msg, Throwable e) {
		super(msg, e);
	}
}
