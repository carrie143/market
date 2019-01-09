package com.gop.exception;

public class AccountErrorException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String errorMessage;

	public AccountErrorException(String arg0) {
		errorMessage = arg0;

	}

	@Override
	public String getMessage() {
		return errorMessage;
	}

}
