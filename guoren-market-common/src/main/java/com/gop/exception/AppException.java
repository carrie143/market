package com.gop.exception;

import lombok.Data;

@Data
public class AppException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private Object attach;
	private String errCode;

	private String message;

	private String[] format;

	public AppException(String errCode) {
		super();
		this.errCode = errCode;
	}

	public AppException(String errCode, String message, String... format) {
		super();
		this.errCode = errCode;
		this.message = message;
		this.format = format;
	}

	public AppException(String errCode, String message, Object attach, String... format) {
		super();
		this.errCode = errCode;
		this.message = message;
		this.format = format;
		this.attach = attach;
	}

	public String getCodeMessage() {
		return this.message;
	}

	@Override
	public String getMessage() {
		return "code:" + this.getErrCode() + ":" + this.message;
	}

}
