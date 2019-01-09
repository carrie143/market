package com.gop.coin.transfer.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.gop.domain.WithdrawCoinOrderUser;

import lombok.Data;

@Data
public class WithdrawDetailDto {

	//@ApiModelProperty("订单创建时间")
	Date createDate;

	//@ApiModelProperty("订单更新时间")
	Date updateDate;

	//@ApiModelProperty("id")
	Integer id;

	//@ApiModelProperty("券商id")
	Integer brokerId;

	//@ApiModelProperty("uid")
	Integer uid;

	//@ApiModelProperty("转入钱包地址")
	String address;

	 //@ApiModelProperty("assetCode")
	 String assetCode;

	//@ApiModelProperty("申请提现数量real_number")
	BigDecimal amount;


	//@ApiModelProperty("实际转入数量/提现金额")
	BigDecimal number;

	//@ApiModelProperty("订单状态")
	String status;

	//@ApiModelProperty("订单附言")
	String msg;
	String addressType;
	BigDecimal txFee;

	//@ApiModelProperty("account")
	String account;
	public WithdrawDetailDto(WithdrawCoinOrderUser order){
		this.setCreateDate(order.getCreateDate());
		this.setUpdateDate(order.getUpdateDate());
		this.setId(order.getId());
		this.setBrokerId(order.getBrokerId());
		this.setUid(order.getUid());
		this.setAddress(order.getCoinAddress());
		this.setAmount(order.getRealNumber());
		this.setStatus(order.getStatus().toString());
		this.setMsg(order.getMsg());
		this.setAddressType(order.getDestAddressType().toString());
		this.setAssetCode(order.getAssetCode());
		this.setAccount(order.getAccount());
		this.setTxFee(order.getTxFee());
		this.setNumber(order.getNumber());
	}
}
