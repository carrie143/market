package com.gop.c2c.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gop.c2c.service.C2cBankInfoService;
import com.gop.code.consts.C2cCodeConst;
import com.gop.domain.C2cBankInfo;
import com.gop.exception.AppException;
import com.gop.mapper.C2cBankInfoMapper;

/**
 * 
 * @author zhangliwei
 *
 */
@Service("C2cBankInfoService")
public class C2cBankInfoServiceImpl implements C2cBankInfoService{
	@Autowired
	private C2cBankInfoMapper c2cBankInfoMapper;

	@Override
	public C2cBankInfo selectById(Integer payChannelId) {
		return c2cBankInfoMapper.selectByPrimaryKey(payChannelId);
	}
	
	
	@Override
	@Transactional
	public void addC2cUserBank(C2cBankInfo c2cBankInfo , Integer uid) {

		
		C2cBankInfo c2cBank  = c2cBankInfoMapper.selectStatusUsingByUid(uid);
		
		if (c2cBank != null) {
			
			int count = c2cBankInfoMapper.deleteById(c2cBank.getId());
			if(count == 1) {
				
				c2cBankInfo.setAddIndex(c2cBank.getAddIndex()+1);
				c2cBankInfoMapper.insertSelective(c2cBankInfo);
			}else {
				
				throw new AppException(C2cCodeConst.CREATE_BANK_ERROR);
			}
		}else {
			
			c2cBankInfo.setAddIndex(1);
			c2cBankInfoMapper.insertSelective(c2cBankInfo);
		}
		
	}

	@Override
	public C2cBankInfo selectByUid(Integer uid) {
		
		C2cBankInfo c2cBankInfo = c2cBankInfoMapper.selectStatusUsingByUid(uid);
		if(c2cBankInfo == null) {
			
			return new C2cBankInfo();
		}
		return c2cBankInfo;
	}
	
	
}
