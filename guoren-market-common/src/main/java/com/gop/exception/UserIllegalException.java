package com.gop.exception;

public class UserIllegalException extends RuntimeException{
	
	private static final long serialVersionUID = 5460643997432618635L;
	
	private String errorMessage;

	public UserIllegalException(String arg0) {
		errorMessage = arg0;

	}

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return errorMessage;
	}

}
