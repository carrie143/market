package com.gop.c2c.service;

import java.util.List;

import com.gop.domain.C2cBuyAdvertisement;
import com.gop.domain.enums.C2cBuyAdvertStatus;
/**
 * 求购广告
 * @author sunhaotian
 *
 */

public interface C2cBuyAdvertisementService {
	
	//添加求购广告
	public void addC2cBuyAdvertisement(C2cBuyAdvertisement c2cBuyAdvertisement);
	
	//查询所有展示中求购广告
	public List<C2cBuyAdvertisement> selectAllByShowStatus(Integer pageNo,Integer pageSize);
	
	//根据求购广告ID查询
	public C2cBuyAdvertisement selectByAdvertId(String advertId);
	
	//确认保存编辑求购广告
	public void ensureEditBuyAdvertisement(C2cBuyAdvertisement c2cBuyAdvertisement);
	
	//更新广告状态为已删除
	public void delBuyAdvertisementByAdvertId(String advertId);
	
	//查询我的求购广告
	public List<C2cBuyAdvertisement> selectC2cBuyAdvertByUid(Integer uid,Integer pageNo,Integer pageSize);
	
	//查询我展示中求购广告
	public List<C2cBuyAdvertisement> selectC2cBuyAdvertByUidAndStatus(Integer uid,C2cBuyAdvertStatus status,Integer pageNo,Integer pageSize);
	
	//查询所有状态的求购广告信息
	public C2cBuyAdvertisement selectAllStatusByAdvertId(String advertId); 
}
