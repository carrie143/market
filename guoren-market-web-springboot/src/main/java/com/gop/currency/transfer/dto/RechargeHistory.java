package com.gop.currency.transfer.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.gop.domain.DepositCurrencyOrderUser;
import com.gop.mode.vo.BaseDto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class RechargeHistory extends BaseDto{
	
	private Date createDate;
	
	private String payMode;
	
	private BigDecimal money;
	
	private BigDecimal fee;
	
	private String Status;
	
	private String AssetCode;
	
	private String accountNo;
	
	private String channelName;
	
	private String thirdAccountNo;
	
	private String thirdAccountName;
	
	private String thridAccountCode;
	
	public RechargeHistory(DepositCurrencyOrderUser order){
		
		this.setAccountNo(order.getAcnumber());
		this.setChannelName(order.getBank());
		this.setAssetCode( order.getAssetCode());
		this.setFee(order.getFee());
		this.setMoney(order.getMoney());
		this.setPayMode(order.getPayMode()!=null?order.getPayMode().toString():"BANK");
		this.setStatus(order.getStatus().toString());
		this.setCreateDate(order.getCreateDate());
		this.setThirdAccountName(order.getThirdAccountName());
		this.setThirdAccountNo(order.getThirdAccount());
		this.setThridAccountCode(order.getThirdAccountCode());
		
	}
}
