package com.gop.user.service;

import java.util.List;

import com.gop.domain.ManagerOperLog;
import com.gop.domain.enums.ManagerOperType;

public interface ManagerOperLogService {
	
	public void recordManagerOperLog(Integer adminId,String ip,ManagerOperType operType,String remark);
	
	public List<ManagerOperLog> selectManagerOperLogByAdminId(Integer adminId,Integer pageNo,Integer pageSize);

}
