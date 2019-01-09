package com.gop.c2c.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gop.c2c.service.C2cAlipayInfoService;
import com.gop.code.consts.C2cCodeConst;
import com.gop.domain.C2cAlipayInfo;
import com.gop.exception.AppException;
import com.gop.mapper.C2cAlipayInfoMapper;
/**
 * 
 * @author zhangliwei
 *
 */
@Service("C2cAlipayInfoService")
public class C2cAlipayInfoServiceImpl implements C2cAlipayInfoService{
	@Autowired
	private C2cAlipayInfoMapper c2cAlipayInfoMapper;
	@Override
	public C2cAlipayInfo selectById(Integer payChannelId) {
		C2cAlipayInfo selectByPrimaryKey = c2cAlipayInfoMapper.selectByPrimaryKey(payChannelId);
		return selectByPrimaryKey;
	}
	
	
	@Override
	@Transactional
	public void addC2cUserAlipay(C2cAlipayInfo c2cAlipayInfo , Integer uid) {

		
		
		
		C2cAlipayInfo c2cAlipay  = c2cAlipayInfoMapper.selectStatusUsingByUid(uid);
		
		if (c2cAlipay !=  null) {
			
			int count = c2cAlipayInfoMapper.deleteById(c2cAlipay.getId());
			if(count == 1) {
				
				c2cAlipayInfo.setAddIndex(c2cAlipay.getAddIndex()+1);
				c2cAlipayInfoMapper.insertSelective(c2cAlipayInfo);
			}else {
				
				throw new AppException(C2cCodeConst.CREATE_ALIPAY_ERROR);
			}
		}else {
			
			c2cAlipayInfo.setAddIndex(1);
			c2cAlipayInfoMapper.insertSelective(c2cAlipayInfo);
		}		
	}
	

	@Override
	public C2cAlipayInfo selectByUid(Integer uid) {
		
		C2cAlipayInfo c2cAlipayInfo = c2cAlipayInfoMapper.selectStatusUsingByUid(uid);
		if(c2cAlipayInfo == null) {
			
			return new C2cAlipayInfo();
		}
		return c2cAlipayInfo;	
	}
	
}
