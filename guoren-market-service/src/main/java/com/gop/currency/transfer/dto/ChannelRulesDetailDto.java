package com.gop.currency.transfer.dto;

import com.gop.domain.ChannelAlipayUserRules;
import com.gop.domain.ChannelOkpayUserRules;

import lombok.Data;

@Data
public class ChannelRulesDetailDto {

	Integer id;
	// @ApiModelProperty("备注")
	String remark;

	// @ApiModelProperty("二维码，目前只有支付宝有二维码")
	String code;

	// @ApiModelProperty("收款账号")
	String account;

	// @ApiModelProperty("收款账号姓名")
	String name;

	// @ApiModelProperty("支付规则开启状态")
	String status;

	// @ApiModelProperty("用户id付款规则")
	String rules;

	public ChannelRulesDetailDto(ChannelOkpayUserRules rules) {
		this.id = rules.getId();
		this.remark = rules.getRemark();
		this.code = rules.getAccountCode();
		this.account = rules.getAccountNo();
		this.name = rules.getAccountName();
		this.status = rules.getStatus().toString();
		this.rules = rules.getRules();
	}

	public ChannelRulesDetailDto(ChannelAlipayUserRules rules) {
		this.id = rules.getId();
		this.remark = rules.getRemark();
		this.code = rules.getAccountCode();
		this.account = rules.getAccountNo();
		this.name = rules.getAccountName();
		this.status = rules.getStatus().toString();
		this.rules = rules.getRules();

	}
}
