package com.gop.asset.Mode.vo;

import lombok.Data;

@Data
public class RealTimeClearInfo {

	private Integer userId;

	private String accountNo;
	
	private Integer brokerId;

	private String assetCode;

	private Integer balanceVersion;

}
