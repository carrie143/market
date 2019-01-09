package com.gop.asset.Mode.vo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class WebApiResponse {
	private String code;
	private String msg;
	private Object data;

}
