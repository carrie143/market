package com.gop.asset.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gop.asset.service.ConfigAssetService;
import com.gop.code.consts.ActivityCodeConst;
import com.gop.code.consts.CommonCodeConst;
import com.gop.domain.ConfigAsset;
import com.gop.exception.AppException;


@RestController
@RequestMapping("/asset-monitor")
public class AssetMonitorController {
	
	@Autowired
	private ConfigAssetService configAssetService;

//	@Autowired
//	private UserAccountFacade userAccountFacade;
	
	@Autowired
	private StringRedisTemplate redisTemplate;
	
	private long expireTime = 1;
	
	private final String prix = "total-query-monitor";
	
	@RequestMapping(value = "/total-query", method = RequestMethod.GET)
	public BigDecimal totalQuery(@RequestParam("assetCode") String assetCode) {
		if(redisTemplate.hasKey(prix)) {
			int count = Integer.valueOf(redisTemplate.opsForValue().get(prix));
			if(count > 2) {
				throw new AppException(CommonCodeConst.FIELD_ERROR,"查询操作过于频繁");
			}
			redisTemplate.boundValueOps(prix).increment(1);
		}else {
			redisTemplate.opsForValue().set(prix, "1", expireTime ,TimeUnit.MINUTES);
		}
		
		// 币种校验
		List<ConfigAsset> availableAssetList = configAssetService.getAvailableAssetCode();
		Set<String> availableAssetSet = availableAssetList.stream().map(c -> c.getAssetCode())
				.collect(Collectors.toSet());
		if (!availableAssetSet.contains(assetCode)) {
			throw new AppException(ActivityCodeConst.FIELD_ERROR, "无效币种");
		}
		
//		return userAccountFacade.queryTotalCountByAssetCode(assetCode);
		return null;
	}
}
