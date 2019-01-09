package com.gop.c2c.dto;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.gop.domain.C2cTransOrder;
import com.gop.domain.enums.C2cTransOrderStatus;

import lombok.Data;

@Data
public class C2cTransOrderDto {
	private String transOrderId;
	private String advertId;
	private Integer buyUid;
	private Integer sellUid;
	private String assetCode;
	private String buyNickname;
	private String sellNickname;
	private BigDecimal number;
	private BigDecimal money;
	private BigDecimal tradePrice;
	private String remark;
	private C2cTransOrderStatus status;
	private Date createDate;
	private Date updateDate;
	private Date currentDate;
	private C2cBasePayChannelDto dto;
	private String payCode;
	
	public C2cTransOrderDto(C2cTransOrder order) {
		this.transOrderId = order.getTransOrderId();
		this.advertId = order.getAdvertId();
		this.buyUid = order.getBuyUid();
		this.sellUid = order.getSellUid();
		this.assetCode = order.getAssetCode();
		this.buyNickname = order.getBuyNickname();
		this.sellNickname = order.getSellNickname();
		this.number = order.getNumber();
		this.money = order.getMoney();
		this.status = order.getStatus();
		this.createDate = order.getCreateDate();
		this.updateDate=order.getUpdateDate();
		this.currentDate = new Date();
		this.remark=order.getRemark();
		this.tradePrice = order.getTradePrice();
		this.payCode = order.getPayCode();
	}
}
