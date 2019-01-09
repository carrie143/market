package com.gop.match.dto;

import com.gop.domain.TradeMatchResult;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class OrderQueryDto {

	//@ApiModelProperty("买单")
	String buyTradeTransferOrderNo;

	//@ApiModelProperty("卖单")
	String sellTradeTransferOrderNo;

	//@ApiModelProperty("卖家uid")
	Integer sellUid;

	//@ApiModelProperty("买家uid")
	Integer buyUid;

	//@ApiModelProperty("时间")
	Date createTime;

	//@ApiModelProperty("价格")
	BigDecimal price;

	//@ApiModelProperty("数量")
	BigDecimal num;

	public OrderQueryDto(TradeMatchResult result){
		this.setBuyTradeTransferOrderNo(result.getBuyInnerOrderNo());
		this.setSellTradeTransferOrderNo(result.getSellInnerOrderNo());
		this.setSellUid(result.getSellUid());
		this.setBuyUid(result.getBuyUid());
		this.setCreateTime(result.getCreateTime());
		this.setPrice(result.getPrice());
		this.setNum(result.getNumber());
	}
	
}
