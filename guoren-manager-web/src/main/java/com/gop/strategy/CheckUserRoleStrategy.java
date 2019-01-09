package com.gop.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.gop.code.consts.SecurityCodeConst;
import com.gop.code.consts.UserCodeConst;
import com.gop.domain.Administrators;
import com.gop.domain.enums.AdministratorsRole;
import com.gop.exception.AppException;
import com.gop.mapper.AdminRoleMapper;
import com.gop.user.service.AdministractorService;
import com.gop.user.service.RoleManagerService;
import com.gop.util.TokenHelper;
import com.gop.web.base.auth.AuthContext;
import com.gop.web.base.auth.strategy.AuthStrategy;

import lombok.extern.slf4j.Slf4j;

@Service("CheckUserRoleStrategy")
@Slf4j
public class CheckUserRoleStrategy implements AuthStrategy {
	@Autowired
	private AdministractorService administractorService;

	@Autowired
	private StringRedisTemplate redisTemplate;
	@Autowired
	private TokenHelper tokenHelper;

	@Override
	public void pre(AuthContext authContext) {
		String token = authContext.getToke();
//		String account = redisTemplate.opsForValue().get(token);

		int uid = 0;
		try {
			uid = tokenHelper.validToken(token);
		} catch (Exception e) {

			throw new AppException(SecurityCodeConst.TOKE_HAS_INVALID);
		}
		if (null == token) {
			throw new AppException(SecurityCodeConst.NO_LOGIN);
		}
		Administrators administrators = administractorService.getAdministractor(uid);
		if (null == administrators) {
			throw new AppException(UserCodeConst.NO_REGISTER);
		}
		//获取角色id
		//获取角色对应接口权限与uri比较
		if (!administractorService.checkRights(administrators.getAdminId(),authContext.getUri())){
			throw new AppException(SecurityCodeConst.NO_PERMISSION);
		}

//		AdministratorsRole role = administrators.getRole();
//		if (!AdministratorsRole.ADMIN.equals(role)) {
//			throw new AppException(SecurityCodeConst.NO_PERMISSION);
//		}

	}

	@Override
	public void after(AuthContext authContext, Throwable throwable) {

	}

	@Override
	public boolean match(AuthContext authContext) {
		return true;
	}
}
