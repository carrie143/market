package com.gop.exception;

public class UlpayBankNullException  extends RuntimeException {

	/**
	 * 
	 */

	private static final long serialVersionUID = -9202877722125812374L;
	private String errorMessage;

	public UlpayBankNullException(String msg) {
		errorMessage = msg;

	}

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return errorMessage;
	}
	

}
