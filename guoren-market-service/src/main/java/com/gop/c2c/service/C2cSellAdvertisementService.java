package com.gop.c2c.service;

import java.util.List;
import java.util.Set;

import com.gop.c2c.dto.C2cAdvertSellBasicInfoDto;
import com.gop.domain.C2cSellAdvertisement;
import com.gop.domain.enums.C2cPayType;
import com.gop.domain.enums.C2cSellAdvertStatus;
import com.gop.mode.vo.PageModel;

/**
 * 售出广告
 * @author sunhaotian
 *
 */
public interface C2cSellAdvertisementService {
	
	public int updateC2cSellAdvertStatusByAdvertId(String advertId,C2cSellAdvertStatus status,C2cSellAdvertStatus beginStatus);
	
	public List<C2cSellAdvertisement> selectAllByAssetCode(String assetCode,Integer pageNo,Integer pageSize);
	
	public C2cSellAdvertisement selectByAdvertId(String advertId);
	
	public List<C2cSellAdvertisement> selectC2cSellAdvertByUid(Integer uid,Integer pageNo,Integer pageSize);
		
	public void addC2cSellAdvertisement(C2cSellAdvertisement c2cSellAdvertisement);
	
	public List<C2cSellAdvertisement> selectC2cSellAdvertByUidAndStatusAndAssetCode(Integer uid,C2cSellAdvertStatus status,String assetCode,Integer pageNo,Integer pageSize);
	
	public void deployC2cSellAdvertisementByUid(C2cSellAdvertisement c2cSellAdvertisement,Set<C2cPayType> paytype);
	
	public void deleteC2cSellAdvertisementByAdvertId(String advertId,Integer uid);
	
	public void ensureEditC2cSellAdvertisementByUid(C2cSellAdvertisement c2cSellAdvertisement,Set<C2cPayType> paytype);
}
