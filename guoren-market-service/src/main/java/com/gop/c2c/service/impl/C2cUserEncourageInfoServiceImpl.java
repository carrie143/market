package com.gop.c2c.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gop.c2c.service.C2cUserEncourageInfoService;
import com.gop.code.consts.CommonCodeConst;
import com.gop.domain.C2cUserEncourageInfo;
import com.gop.exception.AppException;
import com.gop.mapper.C2cUserEncourageInfoMapper;
@Service
public class C2cUserEncourageInfoServiceImpl implements C2cUserEncourageInfoService {
	@Autowired
	private C2cUserEncourageInfoMapper c2cUserEncourageInfoMapper;
	
	@Override
	public Integer getC2cUserEncourageCount(Integer uid) {
 
		C2cUserEncourageInfo c2cUserEncourageInfo = c2cUserEncourageInfoMapper.selectByUid(uid);
		if (c2cUserEncourageInfo == null) {
			return 0;
		}
		Integer count = c2cUserEncourageInfo.getEncouragedCount();
		return count;
	}

	@Override
	public C2cUserEncourageInfo selectByUid(Integer uid) {
		
		C2cUserEncourageInfo c2cUserEncourageInfo = c2cUserEncourageInfoMapper.selectByUid(uid);

		return c2cUserEncourageInfo;
	}

	@Override
	public void addUserEncourageIfo(C2cUserEncourageInfo c2cUserEncourageInfo) {
		
		c2cUserEncourageInfoMapper.insertSelective(c2cUserEncourageInfo);
		
	}

	@Override
	public void updateUserEncourageIfoCountByUid(Integer uid) {
		
		c2cUserEncourageInfoMapper.updateEncouragedCountByUid(uid);
		
	}
	
	

}
