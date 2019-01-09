package com.gop.c2c.service;

import com.gop.domain.C2cOrderRecord;

public interface C2cOrderRecordService {

	public C2cOrderRecord selectOrderByAdvertId(String advertId);
	
	public void addC2cOrderRecordByAdvertId(C2cOrderRecord c2cOrderRecord);
}
