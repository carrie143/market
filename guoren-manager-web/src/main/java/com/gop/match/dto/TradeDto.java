package com.gop.match.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.gop.domain.TradeOrder;
import com.gop.domain.enums.TradeCoinFlag;
import com.gop.domain.enums.TradeCoinStatus;
import com.gop.domain.enums.TradeCoinType;

import lombok.Data;

@Data
public class TradeDto {
	// @ApiModelProperty("Id")
	String id;

	// @ApiModelProperty("UID")
	Integer uid;

	// @ApiModelProperty("价格")
	BigDecimal price;

	// @ApiModelProperty("总量")
	BigDecimal numTotal;

	// @ApiModelProperty("UID")
	BigDecimal numOver;

	BigDecimal tradedNum;

	BigDecimal tradedMoney;

	BigDecimal money;

	// @ApiModelProperty("买卖")
	TradeCoinType tradeCoinType;

	// @ApiModelProperty("状态")
	TradeCoinStatus tradeCoinStatus;

	// @ApiModelProperty("挂单类型")
	TradeCoinFlag tradeCoinFlag;

	// @ApiModelProperty("创建时间")
	Date updateDate;

	// @ApiModelProperty("更新时间")
	Date createDate;

	public TradeDto(TradeOrder order) {
		this.setId(order.getInnerOrderNo());
		this.setUid(order.getUid());
		this.setTradeCoinType(order.getOrderType());
		this.setTradeCoinStatus(order.getStatus());
		this.setTradeCoinFlag(order.getTradeFlag());
		this.setCreateDate(order.getCreateDate());
		this.setUpdateDate(order.getUpdateDate());
		this.setTradedNum(order.getTradedNumber());
		this.setMoney(null == order.getMoney() ? BigDecimal.ZERO : order.getMoney());
		this.setTradedMoney(order.getTradedMoney());
		this.setPrice(order.getPrice());
		if (order.getTradeFlag() == TradeCoinFlag.MARKET && order.getOrderType() == TradeCoinType.BUY) {
			this.setNumOver(order.getMoneyOver());
			this.setNumTotal(order.getMoney());
		} else {
			this.setNumOver(order.getNumberOver());
			this.setNumTotal(order.getNumber());
		}
	}
}
