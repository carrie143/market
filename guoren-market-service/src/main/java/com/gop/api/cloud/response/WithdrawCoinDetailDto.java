package com.gop.api.cloud.response;

import com.gop.api.cloud.request.WithdrawCallbackDto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WithdrawCoinDetailDto {
	private String clientOrderNo;
	//@ApiModelProperty("id")
	private Long id;

	//@ApiModelProperty("订单创建时间")
	private Date createDate;

	//@ApiModelProperty("订单更新时间")
	private Date updateDate;

	//@ApiModelProperty("券商id")
	private String brokerId;

	//@ApiModelProperty("uid")
	private Long uid;

	//@ApiModelProperty("转入钱包地址")
	private String address;

	//@ApiModelProperty("assetCode")
	private String assetCode;

	//@ApiModelProperty("申请提现数量real_number")
	private BigDecimal amount;

	//@ApiModelProperty("实际转入数量/提现金额")
	private BigDecimal number;

	//@ApiModelProperty("订单状态")
	private String status;

	//@ApiModelProperty("订单附言")
	private String msg;

	private BigDecimal platFee;

	private BigDecimal brokerFee;

	//@ApiModelProperty("account")
	String account;

	public WithdrawCoinDetailDto(WithdrawCallbackDto dto){
		this.setUid(dto.getUid());
		this.setClientOrderNo(dto.getClientOrderNo());
		this.setAddress(dto.getCoinAddress());
		this.setStatus(dto.getStatus().name());
		this.setNumber(dto.getNumber());
		this.setAssetCode(dto.getAssetCode());
		this.setCreateDate(dto.getCreateDate());
		this.setUpdateDate(dto.getFinishDate());
		this.setMsg(dto.getMessage());
		this.setAmount(dto.getRealNumber());
	}
}
