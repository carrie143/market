//package com.gop.util;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.gop.code.consts.CommonCodeConst;
//import com.gop.code.consts.SecurityCodeConst;
//import com.gop.domain.UserApiKey;
//import com.gop.exception.AppException;
//import com.gop.notify.service.UserApiKeyService;
//import com.gop.util.dto.ApiRequestDto;
//import com.gop.util.dto.ApiRequestExtractDto;
//
///**
// * @author wangyang 回调的结果和通过api查询的dto一样
// * @param <T>
// */
//@Component
//public class ApiRequestTransaltor {
//	@Autowired
//	private UserApiKeyService userApiKeyService;
//
//	@SuppressWarnings("unchecked")
//	public <T> ApiRequestExtractDto<T> handlerMatchBack(String jsonString, Class<T> t) {
//		ApiRequestDto<JSONObject> apiRequest = null;
//		try {
//			apiRequest = JSON.parseObject(jsonString, ApiRequestDto.class);
//		} catch (Exception e) {
//			throw new AppException(CommonCodeConst.FIELD_ERROR);
//		}
//		if (null == apiRequest) {
//			throw new AppException(CommonCodeConst.FIELD_ERROR, "远程返回值为空");
//
//		}
//		UserApiKey userAPiKey = userApiKeyService.getByUserNo(apiRequest.getBusinessNo());
//		if (null == userAPiKey) {
//			throw new AppException(SecurityCodeConst.SIGN_ERROR, "无效的用户编号");
//		}
//		if (!SignUtil.checkSign(jsonString, userAPiKey.getPassphrase())) {
//			throw new AppException(SecurityCodeConst.SIGN_ERROR);
//		}
//
//		ApiRequestExtractDto<T> apiRequestExtractDto = new ApiRequestExtractDto<>();
//		apiRequestExtractDto.setBrokerId(userAPiKey.getBrokerId());
//		apiRequestExtractDto.setUid(userAPiKey.getUid());
//		try {
//			apiRequestExtractDto.setData(JSONObject.toJavaObject(apiRequest.getData(), t));
//		} catch (Exception e) {
//			throw new AppException(CommonCodeConst.FIELD_ERROR);
//		}
//
//		return apiRequestExtractDto;
//
//	}
//
//}
