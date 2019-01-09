package com.gop.exception;

public class ServiceErrorException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String errorMessage;

	public ServiceErrorException(String arg0) {
		errorMessage = arg0;

	}

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return errorMessage;
	}

}
