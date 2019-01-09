package com.gop.c2c.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gop.c2c.service.C2cTransOrderOperRecordService;
import com.gop.code.consts.CommonCodeConst;
import com.gop.domain.C2cTransOrder;
import com.gop.domain.C2cTransOrderOperRecord;
import com.gop.exception.AppException;
import com.gop.mapper.C2cTransOrderOperRecordMapper;

@Service("C2cTransOrderOperRecordService")
public class C2cTransOrderOperRecordServiceImpl implements C2cTransOrderOperRecordService {
	@Autowired
	private C2cTransOrderOperRecordMapper c2cTransOrderOperRecordMapper;

	@Override
	public void saveTransOrderOperRecord(C2cTransOrderOperRecord c2cTransOrderOperRecord) {
		int result = c2cTransOrderOperRecordMapper.insertSelective(c2cTransOrderOperRecord);
		if (!(result==1)) {
			throw new AppException(CommonCodeConst.SERVICE_ERROR,"订单操作记录添加异常");
		}
	}

	@Override
	public void saveTransOrderOperRecord(C2cTransOrder order, Integer operaUid, String msg,String endStatus) {
		C2cTransOrderOperRecord c2cTransOrderOperRecord = new C2cTransOrderOperRecord();
		c2cTransOrderOperRecord.setTransOrderId(order.getTransOrderId());
		c2cTransOrderOperRecord.setBuyUid(order.getBuyUid());
		c2cTransOrderOperRecord.setSellUid(order.getSellUid());
		c2cTransOrderOperRecord.setOperMsg(msg); // msg可不写,主要以文字记录操用户纠纷时作参考
		c2cTransOrderOperRecord.setOperUid(operaUid);
		c2cTransOrderOperRecord.setBeginStatus(order.getStatus().toString());
		c2cTransOrderOperRecord.setEndStatus(endStatus);
		int result = c2cTransOrderOperRecordMapper.insertSelective(c2cTransOrderOperRecord);
		if (!(result==1)) {
			throw new AppException(CommonCodeConst.SERVICE_ERROR,"订单操作记录添加异常");
		}
	}

}
