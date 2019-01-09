package com.gop.asset.dto;

import java.math.BigDecimal;

import com.gop.financecheck.enums.AccountClass;
import com.gop.financecheck.enums.AccountSubject;
import com.gop.financecheck.enums.BusinessSubject;
import com.gop.mode.vo.BaseDto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class AssetOperationDto extends BaseDto {

	// 账户
	// @NonNull
	private Integer uid;

	// 请求号
	// @NonNull
	private String requestNo;

	// 业务类型（交易，撮合，撮合扣款，转入，提现，人工增减，退款虚拟货币，退款钱）
	// @NonNull
	private BusinessSubject businessSubject;

	// 资产种类
	// @NonNull
	private String assetCode;

	// 资产变化量
	// @NonNull
	private BigDecimal amount;

	// 锁定资产变化量
	// @NonNull
	private BigDecimal lockAmount;

	// 借贷变化量
	// @NonNull
	private BigDecimal loanAmount;

	// // 借贷变化量
	// @NonNull
	private AccountClass accountClass;

	// @NonNull
	private AccountSubject accountSubject;

	private int index;

	private String memo;

}
