package com.kh.secoms.exception;

public class AccessTokenExpiredException extends RuntimeException{
	
	public AccessTokenExpiredException(String message) {
		super(message);
	}
}
