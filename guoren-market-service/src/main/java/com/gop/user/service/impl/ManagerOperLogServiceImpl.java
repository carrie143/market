package com.gop.user.service.impl;



import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.gop.common.GetCountyAndCityByIPService;
import com.gop.domain.ManagerOperLog;
import com.gop.domain.enums.ManagerOperType;
import com.gop.mapper.ManagerOperLogMapper;
import com.gop.user.service.ManagerOperLogService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ManagerOperLogServiceImpl implements ManagerOperLogService {
	
	@Autowired
	@Qualifier("getCountyAndCityByIPServiceImpl")
	private GetCountyAndCityByIPService getCountyAndCityByIPService;	
	
	@Autowired
	private ManagerOperLogMapper managerOperLogMapper;
	
	
	@Override
	public void recordManagerOperLog(Integer adminId, String ip, ManagerOperType operType, String remark) {
		CompletableFuture.runAsync(() -> {
			ManagerOperLog managerOperLog = new ManagerOperLog();
			managerOperLog.setAdminId(adminId);
			managerOperLog.setIpAddress(ip);
			managerOperLog.setOperType(operType.toString());
			managerOperLog.setRemark(remark);
			
			Map<String, String> map = null;
			try {
				if ("Unknow".equals(ip)) {
					managerOperLog.setIpCountry("Unknow");
					managerOperLog.setIpCity("Unknow");
				}else {
					map = getCountyAndCityByIPService.getCountyAndCityByIp(ip);
				}
			} catch (Exception e) {
				managerOperLog.setIpCountry("Unknow");
				managerOperLog.setIpCity("Unknow");
			}
			if (null != map) {
				managerOperLog.setIpCountry(map.get("country"));
				managerOperLog.setIpCity(map.get("city"));
			}
			managerOperLogMapper.addManagerOperLog(managerOperLog);
		});
	}

	@Override
	public List<ManagerOperLog> selectManagerOperLogByAdminId(Integer adminId, Integer pageNo, Integer pageSize) {
		PageHelper.startPage(pageNo, pageSize);
		PageHelper.orderBy("id desc");
		return managerOperLogMapper.selectManagerOperLogByAdminId(adminId);
	}
}
