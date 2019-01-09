package com.gop.mode.vo;

import lombok.Data;

@Data
public class HeaderResult {
	private String resp_time;
	private String req_time;
	private String ret_code;
	private String version;
	private String ret_msg;
}
