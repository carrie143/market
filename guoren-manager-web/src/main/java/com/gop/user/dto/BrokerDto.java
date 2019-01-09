package com.gop.user.dto;

import java.util.Date;

import com.gop.domain.Broker;

import lombok.Data;
@Data
public class BrokerDto {
	
	Long id;
	
	String brokerName;
	
	String brokerNo;
	
	Date createDate;
	
	public BrokerDto(Broker broker){
		this.id = broker.getBrokerId();
		this.brokerName = broker.getFullname();
		this.brokerNo = broker.getBrokerNo();
		this.createDate = broker.getCreateDate();
	}
	
}
