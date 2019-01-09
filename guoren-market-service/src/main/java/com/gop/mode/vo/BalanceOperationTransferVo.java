package com.gop.mode.vo;

import com.gop.financecheck.vo.BalanceOperationVo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class BalanceOperationTransferVo {
	private BalanceOperationVo fromBalance;
	
	private BalanceOperationVo toBalance;
}
