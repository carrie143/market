package com.gop.c2c.service;

import com.gop.domain.C2cTransOrder;
import com.gop.domain.C2cTransOrderOperRecord;

public interface C2cTransOrderOperRecordService {

	public void saveTransOrderOperRecord(C2cTransOrderOperRecord c2cTransOrderOperRecord);
	public void saveTransOrderOperRecord(C2cTransOrder order,Integer operaUid,String msg,String endStatus);
}
