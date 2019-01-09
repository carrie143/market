package com.gop.user.service;

import com.gop.domain.ManagerPasswordOperRecord;
import com.gop.domain.enums.ManagerSetPwdFlag;

public interface ManagerPasswordOperRecordService {
	 
	boolean countSetManagerPassword(Integer amdminId);
	
	void addManagerPasswordOperRecord(Integer adminId,ManagerSetPwdFlag managerSetPwdFlag);
	
	ManagerPasswordOperRecord selectManagerPasswordOperRecord(Integer adminId);
	
	void updateManagerPasswordOperRecordFlagByAdminId(Integer adminId,ManagerSetPwdFlag managerSetPwdFlag);

}
