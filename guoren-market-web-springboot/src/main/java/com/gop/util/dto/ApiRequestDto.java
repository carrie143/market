package com.gop.util.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ApiRequestDto<T> {
	private String businessNo;
	private String sign;
	private T data;
	private String nonceStr;
	private Integer timestamp;
}
