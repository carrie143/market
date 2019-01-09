package com.gop.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class BusinessOrderException extends RuntimeException {
	private static final long serialVersionUID = 9084623353729911521L;
	private String errCode;
	private String errCodeDes;

}
