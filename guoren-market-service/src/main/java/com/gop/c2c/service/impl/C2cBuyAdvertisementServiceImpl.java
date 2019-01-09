package com.gop.c2c.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.google.common.base.Throwables;
import com.gop.c2c.service.C2cBuyAdvertisementService;
import com.gop.code.consts.C2cCodeConst;
import com.gop.code.consts.CommonCodeConst;
import com.gop.domain.C2cBuyAdvertisement;
import com.gop.domain.enums.C2cBuyAdvertStatus;
import com.gop.exception.AppException;
import com.gop.mapper.C2cBuyAdvertisementMapper;
import com.gop.mode.vo.PageModel;
import com.gop.util.OrderUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class C2cBuyAdvertisementServiceImpl implements C2cBuyAdvertisementService{
	
	@Autowired
	private C2cBuyAdvertisementMapper c2cBuyAdvertisementMapper;

	@Override
	public void addC2cBuyAdvertisement(C2cBuyAdvertisement c2cBuyAdvertisement) {
		c2cBuyAdvertisement.setAdvertId(OrderUtil.generateC2cCode());
		c2cBuyAdvertisementMapper.insertSelective(c2cBuyAdvertisement);
	}

	@Override
	public List<C2cBuyAdvertisement> selectAllByShowStatus(Integer pageNo,Integer pageSize) {
		PageHelper.startPage(pageNo, pageSize);
		return c2cBuyAdvertisementMapper.selectAllByShowStatus();
	}

	@Override
	public C2cBuyAdvertisement selectByAdvertId(String advertId) {
		return c2cBuyAdvertisementMapper.selectByAdvertId(advertId);
	}

	@Override
	@Transactional
	public void ensureEditBuyAdvertisement(C2cBuyAdvertisement c2cBuyAdvertisement) {
		try {
			//更新当前广告状态为已删除
			delBuyAdvertisementByAdvertId(c2cBuyAdvertisement.getAdvertId());
			//将编辑后的信息作为新求购广告发布
			addC2cBuyAdvertisement(c2cBuyAdvertisement);
		} catch (Exception e) {
			log.error("用户发布删除广告异常 userId={},eMessage=" + e.getMessage(),c2cBuyAdvertisement.getUid(), e);
			Throwables.propagateIfInstanceOf(e, AppException.class);
			throw new AppException(CommonCodeConst.SERVICE_ERROR);
		}
	}

	@Override
	public void delBuyAdvertisementByAdvertId(String advertId) {
		if(c2cBuyAdvertisementMapper.delBuyAdvertisementByAdvertId(advertId,C2cBuyAdvertStatus.DELETED,C2cBuyAdvertStatus.SHOW) != 1) {
			throw new AppException(C2cCodeConst.ADVERT_HAS_DELETED);
		}
	}

	@Override
	public List<C2cBuyAdvertisement> selectC2cBuyAdvertByUid(Integer uid,Integer pageNo,Integer pageSize) {
		PageHelper.startPage(pageNo, pageSize);
		PageHelper.orderBy("id desc");
		List<C2cBuyAdvertisement> c2cBuyAdvertisements = c2cBuyAdvertisementMapper.selectC2cBuyAdvertByUid(uid);
		return c2cBuyAdvertisements;
	}
	@Override
	public List<C2cBuyAdvertisement> selectC2cBuyAdvertByUidAndStatus(Integer uid,C2cBuyAdvertStatus status,Integer pageNo, Integer pageSize){
		 
		PageHelper.startPage(pageNo, pageSize);
		List<C2cBuyAdvertisement> c2cBuyAdvertisements = c2cBuyAdvertisementMapper.selectC2cBuyAdvertByUidAndStatus(uid, status);
	    return c2cBuyAdvertisements;
	}

	@Override
	public C2cBuyAdvertisement selectAllStatusByAdvertId(String advertId) {
		return c2cBuyAdvertisementMapper.selectAllStatusByAdvertId(advertId);
		
	}
}