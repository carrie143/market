package com.gop.match.dto;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.core.annotation.Order;

import com.gop.domain.TradeMatchResult;

import lombok.Data;

@Data
public class OrderDto {

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

	//@ApiModelProperty("买方手续费")
	BigDecimal buyFee;

	//@ApiModelProperty("卖方手续费")
	BigDecimal sellFee;

	//@ApiModelProperty("Maker/Taker")
	String makerTakerType;


	public OrderDto(TradeMatchResult result){
		this.setBuyTradeTransferOrderNo(result.getBuyInnerOrderNo());
		this.setSellTradeTransferOrderNo(result.getSellInnerOrderNo());
		this.setSellUid(result.getSellUid());
		this.setBuyUid(result.getBuyUid());
		this.setCreateTime(result.getCreateTime());
		this.setPrice(result.getPrice());
		this.setNum(result.getNumber());
		this.setBuyFee(result.getBuyFee());
		this.setBuyFee(result.getSellFee());
		this.setMakerTakerType("");
	}
	
}
