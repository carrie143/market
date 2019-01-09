package com.gop.callback.controller;

import com.alibaba.fastjson.JSONObject;
import com.gop.api.cloud.request.DepositCallbackDto;
import com.gop.api.cloud.request.WithdrawCallbackDto;
import com.gop.asset.service.ConfigAssetService;
import com.gop.code.consts.CommonCodeConst;
import com.gop.coin.transfer.service.DepositCoinService;
import com.gop.coin.transfer.service.WithdrawCoinService;
import com.gop.exception.AppException;
import com.gop.web.base.annotation.AuthForHeader;
import com.gop.web.base.auth.AuthContext;
import com.gop.web.base.auth.annotation.Strategy;
import com.gop.web.base.auth.annotation.Strategys;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController("callbackController")
@RequestMapping("/callback")
@Slf4j
public class CallbackController {

	@Autowired
	ConfigAssetService configAssetService;

	@Autowired
	private WithdrawCoinService withdrawCoinService;

	@Autowired
	private DepositCoinService depositCoinService;

	@Value("${cloud.api.secretkey}")
	private String secretkey;

	@Strategys(strategys = {@Strategy(authStrategy = "exe({'cloudApiCallback'})") })
	@ResponseBody
	@RequestMapping(value = "/cloudapi", method = RequestMethod.POST)
	public void cloudApiCallback(@AuthForHeader AuthContext context, @RequestBody String callback) {
		log.info("请求：{}",callback);
		CloudApiCallback  base = JSONObject.parseObject(callback,CloudApiCallback.class);
		String sign = DigestUtils.sha256Hex(base.getData()+secretkey).toUpperCase();
		log.info("本地签名：{}",sign);
		log.info("请求签名：{}",context.getSign());
		if (!sign.equals(context.getSign())){
			throw new AppException(CommonCodeConst.INVALID_SIGN,"签名错误,"+context.getSign());
		}

		switch(base.getType()){
			case WITHDROW_CONFIME:withdrawCoinService.withdrawConfirmCallback(JSONObject.parseObject(base.getData(), WithdrawCallbackDto.class));break;
			case DEPOSIT_CONFIME:depositCoinService.depositConfirmCallback(JSONObject.parseObject(base.getData(), DepositCallbackDto.class));break;
				default:

		}
	}
}
