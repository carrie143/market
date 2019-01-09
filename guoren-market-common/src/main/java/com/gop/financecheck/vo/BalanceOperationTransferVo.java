package com.gop.financecheck.vo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class BalanceOperationTransferVo {
	private BalanceOperationVo fromBalance;
	
	private BalanceOperationVo toBalance;
}
