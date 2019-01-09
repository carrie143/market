package com.gop.c2c.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.gop.c2c.dto.C2cTransactionInfoDto;
import com.gop.c2c.service.C2cUserEncourageInfoService;
import com.gop.c2c.service.C2cUserTransactionInfoService;
import com.gop.code.consts.CommonCodeConst;
import com.gop.domain.User;
import com.gop.exception.AppException;
import com.gop.offlineorder.service.TokenOrderCompletionCountService;
import com.gop.user.service.UserService;
@Service("C2cUserTransactionInfoService")
/**
 * 
 * @author zhangliwei
 *
 */
public class C2cUserTransactionInfoServiceImpl implements C2cUserTransactionInfoService {

	//获取用户基本信息
	@Autowired
	@Qualifier("userService")
	private UserService userService;
	
	//获取用户基本信息
	@Autowired
	@Qualifier("TokenOrderCompletionCountService")
	private TokenOrderCompletionCountService tokenOrderCompletionCountService;
	
	
	@Autowired
	private C2cUserEncourageInfoService c2cUserEncourageInfoService;
	
	@Override
	public C2cTransactionInfoDto getUserTransactionInfo(Integer uid) {

		User user = userService.getUserByUid(uid);
		if(user == null) {
			
			throw new AppException(CommonCodeConst.FIELD_ERROR);
		}
		Integer totalCount = tokenOrderCompletionCountService.selectTotalByUid(uid);
		C2cTransactionInfoDto c2cTransactionInfoDto = C2cTransactionInfoDto.builder().authLevel(user.getAuthLevel()).uid(user.getUid()).nickName(user.getNickname()).phone(user.getMobile()).encourageCount(c2cUserEncourageInfoService.getC2cUserEncourageCount(uid)).totalCount(totalCount).build();
		return c2cTransactionInfoDto;		
	}
	
}
