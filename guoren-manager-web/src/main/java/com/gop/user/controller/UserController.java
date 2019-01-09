package com.gop.user.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSONObject;
import com.gop.api.cloud.request.UnVerifiedCountRequest;
import com.gop.api.cloud.request.WithdrawQueryRequest;
import com.gop.api.cloud.service.CloudApiService;
import com.gop.code.consts.GoogleCodeConst;
import com.gop.domain.ManagerGoogleCodeConfig;
import com.gop.domain.UserGoogleCodeConfig;
import com.gop.domain.enums.*;
import com.gop.user.service.UserGoogleCodeConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.gop.asset.service.ConfigAssetService;
import com.gop.authentication.dto.UserIdentificationDto;
import com.gop.authentication.facade.AuthenticationFacade;
import com.gop.authentication.service.UserIdentificationService;
import com.gop.authentication.service.UserResidenceService;
import com.gop.code.consts.UserCodeConst;
import com.gop.coin.transfer.service.WithdrawCoinService;
import com.gop.domain.ConfigAsset;
import com.gop.domain.User;
import com.gop.exception.AppException;
import com.gop.mode.vo.PageModel;
import com.gop.user.dto.UnverifiedInfoDto;
import com.gop.user.dto.UserDto;
import com.gop.user.dto.UserInfoDto;
import com.gop.user.service.UserService;
import com.gop.util.TokenHelper;
import com.gop.web.base.annotation.AuthForHeader;
import com.gop.web.base.auth.AuthContext;
import com.gop.web.base.auth.annotation.Strategy;
import com.gop.web.base.auth.annotation.Strategys;

import lombok.extern.slf4j.Slf4j;

@RestController("managerUserController")
@RequestMapping("/user")
@Slf4j
// @Api("用户管理模块")
public class UserController {
	@Autowired
	private TokenHelper tokenHelper;
	@Autowired
	private UserService userService;

	@Autowired
	private AuthenticationFacade authenticationFacade;
	
	@Autowired
	private ConfigAssetService configAssetService;
	
	@Autowired
	private UserIdentificationService userIdentificationService;
	
	@Autowired 
	private UserResidenceService userResidenceService;
	
	@Autowired
	private WithdrawCoinService withdrawCoinService;

	@Autowired
	private CloudApiService cloudApiService;

	@Autowired
	private UserGoogleCodeConfigService userGoogleCodeConfigService;
	// 查询未读消息数量
	// 查询消息
	@Strategys(strategys = { @Strategy(authStrategy = "exe({{'CheckUserRoleStrategy'}})"),
		@Strategy(authStrategy = "exe({{'checkLoginStrategy'}})") })
	@RequestMapping(value = "/user-list", method = RequestMethod.GET)
	// @ApiOperation("用户列表")
	public PageModel<UserDto> userList(@AuthForHeader AuthContext context,
			@RequestParam(value = "uid", required = false) Integer uId,
			@RequestParam(value = "account", required = false) String account,
			@RequestParam(value = "name", required = false) String name, @RequestParam("brokerId") Integer brokerId,
			@RequestParam("pageSize") Integer pageSize, @RequestParam("pageNo") Integer pageNo) {

		PageInfo<User> pageInfo = userService.getUserList(uId, brokerId, account, name, pageNo, pageSize);
		PageModel<UserDto> pageMode = new PageModel<>();
		pageMode.setPageNo(pageNo);
		pageMode.setPageSize(pageSize);
		pageMode.setPageNum(pageInfo.getPages());
		pageMode.setTotal(pageInfo.getTotal());

		List<UserDto> lstDtos = pageInfo.getList().stream().map(user -> new UserDto(user)).collect(Collectors.toList());
		lstDtos.forEach(user -> {
			// 查询已注册用户对应的认证信息与认证类型(默认user_identification表中的审核状态为ok)
			UserIdentificationDto identificationDto = authenticationFacade.getUserIdentificationInfo(user.getUid());
			if (identificationDto != null) {
				user.setCardNo(identificationDto.getCardNo());
				user.setCardType(identificationDto.getCardType());
			}

		});

		pageMode.setList(lstDtos);
		return pageMode;
	}

	@Strategys(strategys = { @Strategy(authStrategy = "exe({{'CheckUserRoleStrategy'}})"),
		@Strategy(authStrategy = "exe({{'checkLoginStrategy'}})") })
	@RequestMapping(value = "/user-info", method = RequestMethod.GET)
	// @ApiOperation("用户信息")
	public UserInfoDto userInfo(@AuthForHeader AuthContext context, @RequestParam("uid") Integer uid) {
		User user = userService.getUserByUid(uid);
		if (null == user) {
			throw new AppException(UserCodeConst.NO_REGISTER);
		}
		UserInfoDto dto = new UserInfoDto(user);

		UserIdentificationDto identificationDto = authenticationFacade.getUserIdentificationInfo(user.getUid());
		if (identificationDto != null) {
			dto.setCardNo(identificationDto.getCardNo());
			dto.setCardType(identificationDto.getCardType());
		}

		return dto;
	}

	// 后台管理员待审信息统计提醒(充值,体现,地址待审核,身份待审核)
	@Strategys(strategys = { @Strategy(authStrategy = "exe({{'CheckUserRoleStrategy'}})"),
		@Strategy(authStrategy = "exe({{'checkLoginStrategy'}})") })
	@RequestMapping(value = "/unverified", method = RequestMethod.GET)
	public UnverifiedInfoDto getUserUndisposedInfo(@AuthForHeader AuthContext context) {
		//查询平台listed状态的币种列表
		List<ConfigAsset> assetList = new ArrayList<>();
		assetList = configAssetService.getAvailableAssetCode();
		// 查询提币待审核数量(需要币种区分)
		HashMap<String, Integer> withdrawCoinMap = new HashMap<>();
		for (ConfigAsset configAsset : assetList) {
			// 查询状态的数量(wait)
			UnVerifiedCountRequest request = new UnVerifiedCountRequest();
			request.setStatus(WithdrawCoinOrderStatus.WAIT);
			request.setAssetCode(configAsset.getAssetCode());
			Map<String,Integer> waitCounts = cloudApiService.countWithdrawByStatus(request);
			withdrawCoinMap.put(configAsset.getAssetCode(), waitCounts.get(configAsset.getAssetCode()));
		}
		// 查询身份认证待审核数量
		Integer identificationInitNum = userIdentificationService.getAmountOfIdentificationWithStatus(AuditStatus.INIT);
		// 查询地址认证待审核数量
		Integer residenceInitNum = userResidenceService.getAmountOfResidenceWithStatus(AuditStatus.INIT);
		// 返回dto
		UnverifiedInfoDto unverifiedInfoDto = new UnverifiedInfoDto();
		unverifiedInfoDto.setIdentificationInitNum(identificationInitNum);
		unverifiedInfoDto.setResidenceInitNum(residenceInitNum);
		unverifiedInfoDto.setWithdrawCoinMap(withdrawCoinMap);
		return unverifiedInfoDto;
	}

	//超管重置谷歌验证码
	@Strategys(strategys = { @Strategy(authStrategy = "exe({{'CheckUserRoleStrategy'}})"),
													 @Strategy(authStrategy = "exe({{'checkLoginPasswordStrategy'}})"),
		@Strategy(authStrategy = "exe({{'checkLoginStrategy'}})")})
	@RequestMapping(value = "/user_googlecode-reset", method = RequestMethod.GET)
	public void googleCodeReset(@AuthForHeader AuthContext context,@RequestParam("uid") Integer uid) {
		UserGoogleCodeConfig userGoogleCodeConfig = userGoogleCodeConfigService.selectUserGoogleCodeConfigByUid(uid);
		if (null == userGoogleCodeConfig || UserGoogleCodeFlagType.OFF.equals(userGoogleCodeConfig.getFlag())) {
			throw new AppException(GoogleCodeConst.MANAGER_CAN_NOT_RESET_GOOGLE_CODE);
		}
		userGoogleCodeConfigService.updateGoogleCodeConfigByUid(uid, UserGoogleCodeFlagType.OFF);
	}

	@Strategys(strategys = { @Strategy(authStrategy = "exe({{'checkLoginStrategy'}})")})
	@RequestMapping(value = "/freshen-token", method = RequestMethod.GET)
	public JSONObject freshentoken(@AuthForHeader AuthContext authContext) {
		String token = authContext.getToke();
		tokenHelper.validToken(token);
		JSONObject json = new JSONObject();
		json.put("token", token);
		return json;
	}
}
