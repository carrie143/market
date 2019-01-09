package com.gop.coin.transfer.dto;

import lombok.Data;

@Data
public class ApiTransferOutReturnDto {

	String resultCode;
	Integer uid;
	String orderNo;
	String status;
	String toAddress;
	String amount;
	String message;

}
