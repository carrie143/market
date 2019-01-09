package com.gop.c2c.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.gop.domain.enums.C2cPayType;
import com.gop.domain.enums.C2cSellAdvertStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class C2cAdvertSellBasicInfoDto {
	//昵称
	private String nickName; 
	//交易币种
	private String assetcode;
	//国家
	private String country;
	//货币
	private String currency;
	//手机号
	private String phone;
	//交易价格
	private BigDecimal tradePrice; 
	//最小售出个数
	private BigDecimal minAmount; 
	//最大售出个数
	private BigDecimal maxAmount;
	//最小售出金额
	private BigDecimal minMoney;
	//最大售出金额
	private BigDecimal maxMoney;
	//备注信息
	private String remark;
	//支付方式
	private List<C2cPayType> payType;
	//广告单号
	private String advertId;
	//广告状态
	private C2cSellAdvertStatus status;
	//卖方用户uid
	private Integer uid;
	//创建时间
	private Date createDate;
}
