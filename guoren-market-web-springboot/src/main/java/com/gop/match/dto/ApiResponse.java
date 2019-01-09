package com.gop.match.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ApiResponse {
	private String returnCode;
	private String resultCode;
	private String errCode;
	private String errCodeDes;
	private String returnMsg;
	private Object data;
}
