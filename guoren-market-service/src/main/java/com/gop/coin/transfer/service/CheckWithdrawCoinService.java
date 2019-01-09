package com.gop.coin.transfer.service;

import java.math.BigDecimal;

import com.gop.domain.enums.AuthLevel;
import com.gop.domain.enums.ConfigAssetType;
/**
 * @author caleb
 *
 */
public interface CheckWithdrawCoinService {
	/**
	 * 校验用户的对应等级的每日转笔限定,最小值限定,最大值限定等
	 * @param uid
	 * @param userAuthLevel
	 * @param assetCode
	 * @param amount
	 */
	public void checkWithdrawCoinOrder(Integer uid,AuthLevel userAuthLevel,String assetCode,BigDecimal amount);
	/**
	 * 提供用户单独查询对应币相应的费用限定
	 * @param assetCode
	 * @param type
	 * @return
	 */
	public BigDecimal getWithdrawAmount(String assetCode,ConfigAssetType type);
}
