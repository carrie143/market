package com.gop.currency.transfer.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gop.code.consts.AccountCodeConst;
import com.gop.code.consts.RechargeCodeConst;
import com.gop.coin.transfer.service.ConfigAssetProfileService;
import com.gop.common.Environment;
import com.gop.currency.transfer.dto.DepositOrderReturnDto;
import com.gop.currency.transfer.service.ChannelCurrencyDepositOrderService;
import com.gop.currency.transfer.service.DepositCurrencyQueryOrderService;
import com.gop.domain.ChannelBankUserAccount;
import com.gop.domain.DepositCurrencyOrderUser;
import com.gop.domain.enums.DelFlag;
import com.gop.domain.enums.DepositCurrencyOrderStatus;
import com.gop.domain.enums.DepositCurrencyPayMode;
import com.gop.exception.AppException;
import com.gop.mapper.ChannelBankUserAccountMapper;
import com.gop.mapper.DepositCurrencyOrderUserMapper;
import com.gop.user.dto.UserSimpleInfoDto;
import com.gop.user.facade.UserFacade;
import com.gop.util.BigDecimalUtils;
import com.gop.util.OrderUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DepositBankOrderServiceImpl implements ChannelCurrencyDepositOrderService {
	@Override
	public DepositOrderReturnDto depositOrder(Integer channelWithdrawId, String assetCode, Integer uid,
			BigDecimal money, String memo) {
		// TODO Auto-generated method stub
		return null;
	}

}
