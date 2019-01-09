package com.gop.util.dto;

import lombok.Data;

@Data
public class ApiRequestExtractDto<T> {
	private int uid;

	private int brokerId;

	private T data;

}
