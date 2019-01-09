package com.gop.c2c.service;

import com.gop.domain.C2cOrderCancelRecord;


/**
 * C2c取消订单记录
 * @author sunhaotian
 *
 */
public interface C2cOrderCancelRecordService {
	
	public void addC2cOrderCancelRecordByOrderId(C2cOrderCancelRecord c2cOrderCancelRecord);

}
