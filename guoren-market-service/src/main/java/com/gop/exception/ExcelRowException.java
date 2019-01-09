package com.gop.exception;

import lombok.Data;

@Data
public class ExcelRowException extends RuntimeException {
	public ExcelRowException(Integer column, String errorMsg) {
		this.column = column;
		this.errorMsg = errorMsg;
	}

	private Integer column;
	private String errorMsg;
}
