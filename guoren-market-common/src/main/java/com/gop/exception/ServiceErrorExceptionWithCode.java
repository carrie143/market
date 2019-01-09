package com.gop.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ServiceErrorExceptionWithCode extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String errorMessage;
	private String errorCode;
	public static final ServiceErrorExceptionWithCode COMMON_SERVICE_ERROR=new ServiceErrorExceptionWithCode("500","服务异常");
	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return errorMessage;
	}
	public String getErrorCode() {
		// TODO Auto-generated method stub
		return errorCode;
	}	
}
