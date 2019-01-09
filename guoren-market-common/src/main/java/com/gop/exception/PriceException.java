package com.gop.exception;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PriceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String code;

	private String msg;

	private String[] format;

	public PriceException(String code, String msg, String... format) {
		this.msg = msg;
		this.code = code;
		this.format = format;
	}

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return msg;
	}

}
