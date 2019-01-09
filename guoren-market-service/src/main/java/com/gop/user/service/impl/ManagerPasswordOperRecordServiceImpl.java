package com.gop.user.service.impl;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gop.domain.ManagerPasswordOperRecord;
import com.gop.domain.enums.ManagerSetPwdFlag;
import com.gop.mapper.ManagerPasswordOperRecordMapper;
import com.gop.user.service.ManagerPasswordOperRecordService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ManagerPasswordOperRecordServiceImpl implements ManagerPasswordOperRecordService {
	
	
	@Autowired
	private ManagerPasswordOperRecordMapper managerPasswordOperRecordMapper;
	
	@Override
	public boolean countSetManagerPassword(Integer adminId) {
		ManagerPasswordOperRecord managerPasswordOperRecord = managerPasswordOperRecordMapper.selectManagerPasswordOperRecord(adminId);
		if (null != managerPasswordOperRecord) {
			if (ManagerSetPwdFlag.TRUE.equals(managerPasswordOperRecord.getModifyFlag())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void addManagerPasswordOperRecord(Integer adminId, ManagerSetPwdFlag managerSetPwdFlag) {
		ManagerPasswordOperRecord  record = new ManagerPasswordOperRecord();
		record.setAdminId(adminId);
		record.setModifyFlag(managerSetPwdFlag);
		managerPasswordOperRecordMapper.addManagerPasswordOperRecord(record);
	}

	@Override
	public ManagerPasswordOperRecord selectManagerPasswordOperRecord(Integer adminId) {
		return managerPasswordOperRecordMapper.selectManagerPasswordOperRecord(adminId);
	}

	@Override
	public void updateManagerPasswordOperRecordFlagByAdminId(Integer adminId, ManagerSetPwdFlag managerSetPwdFlag) {
		managerPasswordOperRecordMapper.updateManagerPasswordOperRecordFlagByAdminId(adminId, managerSetPwdFlag);
	}
}
