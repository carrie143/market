package com.gop.strategy;

import com.gop.code.consts.CommonCodeConst;
import com.gop.exception.AppException;
import com.gop.web.base.auth.AuthContext;
import com.gop.web.base.auth.strategy.AuthStrategy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service("cloudApiCallback")
@Slf4j
public class CloudApiCallbackStrategy implements AuthStrategy {

	@Value("${cloud.api.accesskey}")
	private String accesskey;

	@Override
	public void pre(AuthContext authContext) {
		if (authContext.getAccesskey()==null || !authContext.getAccesskey().equals(accesskey)){
			throw new AppException(CommonCodeConst.INVALID_REQUEST,"accesskey错误:" + authContext.getAccesskey());
		}
		if (authContext.getSign()==null){
			throw new AppException("签名不存在");
		}
	}

	@Override
	public void after(AuthContext authContext, Throwable throwable) {

	}

	@Override
	public boolean match(AuthContext authContext) {
		return true;
	}
}
