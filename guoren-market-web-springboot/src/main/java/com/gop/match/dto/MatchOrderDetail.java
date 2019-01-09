package com.gop.match.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.gop.domain.TradeOrder;
import com.gop.domain.enums.TradeCoinFlag;
import com.gop.domain.enums.TradeCoinStatus;
import com.gop.domain.enums.TradeCoinType;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class MatchOrderDetail {

	private Date createTime;

	private String tradedNumber;

	private String numberOver;
	// 交易号
	private String orderNo;
	private String payTransactionNo;
	// 下单类型
	private TradeCoinType tradeCoinType;
	// 下单种类
	private TradeCoinFlag tradeCoinFlag;
	// 交易对
	private String symbol;
	// 下单价格
	private  String price;
	// 下单数量
	private String number;
	// 成交金额
	private String matchedMoney;
	//收取手续费币种
	private String feeAsset;
	//券商用户扣除手续费
	private String fee;

	// 交易状态
	private TradeCoinStatus tradeCoinStatus;


}
