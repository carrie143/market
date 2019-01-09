package com.gop.coin.transfer.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.gop.domain.DepositCoinOrderUser;
import com.gop.domain.enums.DepositCoinAssetStatus;

import lombok.Data;

@Data
public class DepositDetailDto {

	// @ApiModelProperty("订单创建时间")
	Date createDate;

	// @ApiModelProperty("id")
	Integer id;

	// @ApiModelProperty("券商id")
	Integer brokerId;

	// @ApiModelProperty("uid")
	Integer uid;

	// @ApiModelProperty("转入钱包地址")
	String address;

	 //@ApiModelProperty("assetCode")
	 String assetCode;

	// @ApiModelProperty("转入数量")
	BigDecimal amount;

	// @ApiModelProperty("订单状态")
	DepositCoinAssetStatus status;

	// @ApiModelProperty("订单附言")
	String msg;

	// @ApiModelProperty("账号信息")
	String account;

	public DepositDetailDto(DepositCoinOrderUser order) {
		this.setCreateDate(order.getCreateDate());
		this.setId(order.getId());
		this.setBrokerId(order.getBrokerId());
		this.setUid(order.getUid());
		this.setAddress(order.getCoinAddress());
		this.setAmount(order.getRealNumber());
		this.setStatus(order.getAssetStatus());
		this.setMsg(order.getMsg());
		this.setAccount(order.getAccount());
		this.setAssetCode(order.getAssetCode());
	}

}
