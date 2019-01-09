package com.gop.strategy;

import java.math.BigDecimal;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;

import com.gop.code.consts.WithdrawalsCodeConst;
import com.gop.coin.transfer.dto.WithDrawCoinDto;
import com.gop.coin.transfer.service.ConfigAssetProfileService;
import com.gop.exception.AppException;
import com.gop.util.BigDecimalUtils;
import com.gop.web.base.auth.AuthContext;
import com.gop.web.base.auth.strategy.AuthStrategy;

import lombok.extern.slf4j.Slf4j;

/**
 * 检查用户请求中的
 * 
 * 非通用策略,暂时作废,可删除
 * 
 * @author caleb
 *
 */
@Service("CheckWithdrawCoinOderAmountLimitStrategy")
@Slf4j
public class CheckWithdrawCoinOderAmountLimitStrategy implements AuthStrategy {

	@Autowired
	private ConfigAssetProfileService configAssetProfileService;
	// private static HashMap<String, BigDecimal> amountCache ;
	NativeWebRequest webRequest;

	@Override
	public void pre(AuthContext authContext) {
		WithDrawCoinDto dto = (WithDrawCoinDto) webRequest.getAttribute("dto", RequestAttributes.SCOPE_REQUEST);
		BigDecimal amount = dto.getAmount();
		String assetCode = dto.getAssetCode();
		// if( null == amountCache ) {
		//
		// }
		BigDecimal minWithdrawAmount = null;
		BigDecimal maxWithdrawAmount = null;
		BigDecimal dailyMaxWithdrawAmount = null;
		// amountCache.put("minWithdrawAmount", minWithdrawAmount);
		// amountCache.put("maxWithdrawAmount", maxWithdrawAmount);
		// amountCache.put("dailyaxWithdrawAmount", dailyMaxWithdrawAmount);

		if (BigDecimalUtils.isLess(amount, minWithdrawAmount)) {

			throw new AppException(WithdrawalsCodeConst.LESS_MIN_WITHDRAWAL_COIN_AMOUNT, "转出值过小",
					minWithdrawAmount.toPlainString());
		}

		if (BigDecimalUtils.isBigger(amount, maxWithdrawAmount)) {

			throw new AppException(WithdrawalsCodeConst.SUPER_MAX_WITHDRAWAL_COIN_AMOUNT, "转出值过大",
					maxWithdrawAmount.toPlainString());
		}
		// if (BigDecimalUtils.isBigger(amount, dailyaxWithdrawAmount)) {
		//
		// throw new
		// AppException(WithdrawalsCodeConst.SUPER_DAILY_MAX_WITHDRAWAL_COIN_AMOUNT,
		// "转出值超过每日额度",
		// dailyaxWithdrawAmount.toPlainString());
		// }
	}

	@Override
	public boolean match(AuthContext authContext) {

		return true;
	}

	@Override
	public void after(AuthContext authContext, Throwable throwable) {
		// TODO Auto-generated method stub

	}

}
