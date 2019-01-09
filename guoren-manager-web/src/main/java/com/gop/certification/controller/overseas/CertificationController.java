package com.gop.certification.controller.overseas;

import java.util.*;
import java.util.stream.Collectors;

import com.github.pagehelper.PageInfo;
import com.gop.authentication.dto.*;
import com.gop.common.ExportExcelService;
import com.gop.user.dto.UserBaseDto;
import com.gop.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;
import com.gop.authentication.facade.AuthenticationFacade;
import com.gop.authentication.service.ImageService;
import com.gop.authentication.service.UserBasicInfoService;
import com.gop.authentication.service.UserIdentificationService;
import com.gop.authentication.service.UserResidenceService;
import com.gop.certification.controller.dto.UserDetailDto;
import com.gop.certification.controller.dto.UserInfoDto;
import com.gop.certification.controller.dto.UserinfoParamType;
import com.gop.code.consts.CommonCodeConst;
import com.gop.code.consts.MessageConst;
import com.gop.common.SmsMessageService;
import com.gop.conetxt.EnvironmentContxt;
import com.gop.domain.User;
import com.gop.domain.UserBasicInfo;
import com.gop.domain.UserIdentification;
import com.gop.domain.UserLoginLog;
import com.gop.domain.UserResidence;
import com.gop.domain.enums.AuditDealStatus;
import com.gop.domain.enums.AuditStatus;
import com.gop.exception.AppException;
import com.gop.mode.vo.PageModel;
import com.gop.user.facade.UserFacade;
import com.gop.user.service.UserLoginLogService;
import com.gop.user.service.UserMessageService;
import com.gop.web.base.annotation.AuthForHeader;
import com.gop.web.base.auth.AuthContext;
import com.gop.web.base.auth.annotation.Strategy;
import com.gop.web.base.auth.annotation.Strategys;

import lombok.extern.slf4j.Slf4j;

@RestController("overseasManagerCertificationController")
@RequestMapping("/security/overseas")
@Slf4j
// @Api("用户中心")
public class CertificationController {

	@Autowired
	private UserFacade userFacade;

	@Autowired
	private ImageService imageService;

	@Autowired
	private UserMessageService cmsNewsService;

	@Autowired
	private SmsMessageService smsMessageService;

	@Autowired
	private EnvironmentContxt environmentContxt;

	@Autowired
	private UserBasicInfoService userBasicInfoService;

	@Autowired
	private UserIdentificationService userIdentificationService;

	@Autowired
	private UserResidenceService userResidenceService;

	@Autowired
	private AuthenticationFacade authenticationFacade;
	
	@Autowired
	private UserLoginLogService userLoginLogService;

	@Autowired
	private UserService userService;

	@Autowired
	private ExportExcelService exportExcelService;

	@Strategys(strategys = { @Strategy(authStrategy = "exe({{'CheckUserRoleStrategy'}})"),
		@Strategy(authStrategy = "exe({{'checkLoginStrategy'}})") })
	@RequestMapping(value = "user-list", method = RequestMethod.GET)
	// @ApiOperation("获取用户信息列表")
	public PageModel<UserBaseDto> getUserDetail(@AuthForHeader AuthContext context,
									   @RequestParam(value = "uid", required = false) Integer uId,
									   @RequestParam(value = "email", required = false) String email,
									   @RequestParam(value = "fullname", required = false) String fullname,
									   @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date startDate,
									   @RequestParam(value = "endDate", required = false)  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endDate,
									   @RequestParam(value = "sortProp", required = false) String sortProp,
									   @RequestParam(value = "sortOrder", required = false) String sortOrder,
									   @RequestParam("pageSize") Integer pageSize, @RequestParam("pageNo") Integer pageNo) {

		String orderBy = null;
		if(sortProp == null || sortOrder == null){
			orderBy = "create_date desc";
		}else{
			orderBy = sortProp+" "+sortOrder;
		}
		PageInfo<User> pageInfo = userService.getBaseUserList(uId, email, fullname,startDate,endDate,orderBy, pageNo, pageSize);
		PageModel<UserBaseDto> pageMode = new PageModel<>();
		pageMode.setPageNo(pageNo);
		pageMode.setPageSize(pageSize);
		pageMode.setPageNum(pageInfo.getPages());
		pageMode.setTotal(pageInfo.getTotal());

		List<UserBaseDto> lstDtos = pageInfo.getList().stream().map(user -> new UserBaseDto(
				user,
				userLoginLogService.getFirstLoginByUid(user.getUid())
				)).collect(Collectors.toList());

		pageMode.setList(lstDtos);
		return pageMode;
	}

	// ***********************quickdax版添加************************
	@Strategys(strategys = { @Strategy(authStrategy = "exe({{'CheckUserRoleStrategy'}})"),
		@Strategy(authStrategy = "exe({{'checkLoginStrategy'}})") })
	@RequestMapping(value = "user-detail", method = RequestMethod.GET)
	// @ApiOperation("获取身份证认证信息列表")
	public UserDetailDto getUserDetail(@AuthForHeader AuthContext context,
			@RequestParam(value = "type") UserinfoParamType type, @RequestParam(value = "value") String value) {
		if (!type.validateValue(value)) {
			throw new AppException(CommonCodeConst.FIELD_ERROR);
		}
		User user = UserinfoParamType.EMAIL.equals(type) ? userFacade.getUser(value)
				: userFacade.getUser(Integer.valueOf(value));
		if (null == user) {
			throw new AppException(CommonCodeConst.FIELD_ERROR);
		}
		UserLoginLog userLog = userLoginLogService.getFirstLoginByUid(user.getUid());
		user.setCreateip(null != userLog? userLog.getIpAddress():"信息缺失");
		UserBasicInfo userBasicInfo = userBasicInfoService.getBasicInfoByUid(user.getUid());
		UserIdentification userIdentification = userIdentificationService
				.getUserByUidAndStatusAndAuditStatus(user.getUid(), AuditDealStatus.FINISH, AuditStatus.OK);
		UserResidence userResidence = userResidenceService.getUserByUidAndStatusAndAuditStatus(user.getUid(),
				AuditDealStatus.FINISH, AuditStatus.OK);
		List<UserIdentification> identList = userIdentificationService.getIdentityHistoryListByLimitNo(user.getUid(),
				null);

		List<UserResidence> residenceList = userResidenceService.getIdentityHistoryListByLimitNo(user.getUid(), null);
		UserDetailDto userDetailDto = new UserDetailDto();
		UserInfoDto userInfoDto = new UserInfoDto(user);
		userInfoDto.setIpCity(null != userLog? userLog.getIpCity():"信息缺失");
		userDetailDto.setUserInfoDto(userInfoDto);
		userDetailDto.setUserBasicInfo(null != userBasicInfo ? userBasicInfo : new UserBasicInfo());
		userDetailDto.setUserIdentification(null != userIdentification ? userIdentification : new UserIdentification());
		userDetailDto.setUserResidence(null != userResidence ? userResidence : new UserResidence());
		userDetailDto.setUserIdentifications(null != identList ? identList : Lists.newArrayList());
		userDetailDto.setUserResidences(null != residenceList ? residenceList : Lists.newArrayList());
		return userDetailDto;
	}

	/**
	 * 获取身份认证信息列表
	 * 
	 * @param status
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@Strategys(strategys = { @Strategy(authStrategy = "exe({{'CheckUserRoleStrategy'}})"),
		@Strategy(authStrategy = "exe({{'checkLoginStrategy'}})") })
	@RequestMapping(value = "/identities", method = RequestMethod.GET)
	// @ApiOperation("获取身份证认证信息列表")
	public PageModel<UserIdentification> getIdentityInfoList(@AuthForHeader AuthContext context,
			@RequestParam(value = "uid", required = false) Integer uid,
			@RequestParam(value = "email", required = false) String email,
			@RequestParam(value = "status", required = false) AuditDealStatus status,
			@RequestParam("pageNo") Integer pageNo, @RequestParam("pageSize") Integer pageSize) {
		if (null != email) {
			User user = userFacade.getUser(email);
			if (null != user) {
				uid = user.getUid();
			}
		}
		// ************************** 1.5.1 版本 *********************
		// 1.5.1
		// 获取证件审核历史(uid)(,根据AuditDealStatus来区分待审核与审核历史)
		PageModel<UserIdentification> userIdentificationPageModel = userIdentificationService
				.getUserIdentificationPageModel(uid, status, pageNo, pageSize);
		return userIdentificationPageModel;

	}

	/**
	 * 获取身份认证详情
	 * 
	 * @return
	 */
	@Strategys(strategys = { @Strategy(authStrategy = "exe({{'CheckUserRoleStrategy'}})"),
		@Strategy(authStrategy = "exe({{'checkLoginStrategy'}})") })
	@RequestMapping(value = "/identity-detail", method = RequestMethod.GET)
	// @ApiOperation("获取身份认证详情")
	public UserAuthLevel0Dto getIdentityInfoById(@AuthForHeader AuthContext context, @RequestParam("id") Integer id) {

		// ************************** 1.5.1 版本 *********************
		// 根据id获取证件审核信息
		UserIdentification userIdentification = userIdentificationService.getserIdentificationById(id);
		if (null == userIdentification) {
			throw new AppException(CommonCodeConst.FIELD_ERROR);
		}
		// 获取身份信息(uid)
		UserBasicInfo userBasicInfo = userBasicInfoService.getBasicInfoByUid(userIdentification.getUid());

		UserAuthLevel0Dto userAuthLevel0Dto = new UserAuthLevel0Dto();
		userAuthLevel0Dto.setUserBasicInfoDto(new UserBasicInfoDto());
		userAuthLevel0Dto.setIdentificationDto(new IdentificationDto());

		UserBasicInfoDto userBasicInfoDto = new UserBasicInfoDto(userBasicInfo);

		IdentificationDto identificationDto = new IdentificationDto(userIdentification);

		userAuthLevel0Dto.setUserBasicInfoDto(userBasicInfoDto);
		userAuthLevel0Dto.setIdentificationDto(identificationDto);
		// 获取当前用户最后五次审核
		List<UserIdentification> list = userIdentificationService
				.getIdentityHistoryListByLimitNo(userIdentification.getUid(), 5);
		userAuthLevel0Dto.setUserIdentificationList(list);
		return userAuthLevel0Dto;
	}

	/**
	 * 审核身份认证信息
	 * 
	 * @return
	 */
	@Strategys(strategys = { @Strategy(authStrategy = "exe({{'CheckUserRoleStrategy'}})"),
		@Strategy(authStrategy = "exe({{'checkLoginStrategy'}})"),
			@Strategy(authStrategy = "exe({{'checkLoginPasswordStrategy'}})") })
	@RequestMapping(value = "/identity-audit", method = RequestMethod.GET)
	// @ApiOperation("审核身份认证信息")
	@Transactional
	public void IdentityAuthenticationAudit(@AuthForHeader AuthContext context, @RequestParam("id") Integer id,
			@RequestParam("auditStatus") AuditStatus auditStatus, @RequestParam("auditMessageId") String auditMessageId,
			@RequestParam("auditMessage") String auditMessage) {

		int adminId = context.getLoginSession().getUserId();
		// ********** 1.5.1 ***************
		// 设置邮件信息
		String sendMessage = "";
		if (AuditStatus.OK.equals(auditStatus)) {
			sendMessage = environmentContxt.getMsg(MessageConst.ID_VERIFY_SUCCESS_MESSAGE, auditMessage);
		}
		if (AuditStatus.FAIL.equals(auditStatus)) {
			sendMessage = environmentContxt.getMsg(MessageConst.ID_VERIFY_FAIL_MESSAGE, auditMessage);
		}
		// 身份认证update
		authenticationFacade.auditUserLevel0To1(adminId, id, auditStatus, auditMessageId, auditMessage, sendMessage);

	}

	/**
	 * 获取居住认证信息列表
	 * 
	 * @param status
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@Strategys(strategys = { @Strategy(authStrategy = "exe({{'CheckUserRoleStrategy'}})"),
		@Strategy(authStrategy = "exe({{'checkLoginStrategy'}})") })
	@RequestMapping(value = "/residences", method = RequestMethod.GET)
	// @ApiOperation("获取居住认证信息列表")
	public PageModel<UserResidence> getResidenceAuthenticationList(@AuthForHeader AuthContext context,
			@RequestParam(value = "uid", required = false) Integer userId,
			@RequestParam(value = "email", required = false) String email,
			@RequestParam(value = "status", required = false) AuditDealStatus status,
			@RequestParam("pageNo") Integer pageNo, @RequestParam("pageSize") Integer pageSize) {
		if (null != email) {
			User user = userFacade.getUser(email);
			if (null != user) {
				userId = user.getUid();
			}
		}
		// ************************** 1.5.1 版本 *********************
		// 1.5.1
		// 获取居住地审核历史(uid)
		PageModel<UserResidence> userResidencePageModel = userResidenceService.getUserResidencePageModel(userId, status,
				pageNo, pageSize);
		return userResidencePageModel;
	}

	/**
	 * 获取居住认证详情
	 * 
	 * @return
	 */
	@Strategys(strategys = { @Strategy(authStrategy = "exe({{'CheckUserRoleStrategy'}})"),
		@Strategy(authStrategy = "exe({{'checkLoginStrategy'}})") })
	@RequestMapping(value = "/residence-detail", method = RequestMethod.GET)
	public UserResidenceDto getResidenceInfoById(@AuthForHeader AuthContext context, @RequestParam("id") Integer id) {
		// 根据id获取居住地审核信息(id)
		UserResidence userResidence = userResidenceService.getUserResidenceInfoById(id);
		List<UserResidence> list = userResidenceService.getIdentityHistoryListByLimitNo(userResidence.getUid(), 5);
		UserBasicInfo userBasicInfo = userBasicInfoService.getBasicInfoByUid(userResidence.getUid());

		UserResidenceDto userResidenceDto = new UserResidenceDto();
		userResidenceDto.setUserBasicInfo(userBasicInfo);
		userResidenceDto.setUserResidence(userResidence);
		userResidenceDto.setUserResidenceList(list);
		return userResidenceDto;
	}

	/**
	 * 审核居住认证信息
	 * 
	 * @return
	 */
	@Strategys(strategys = { @Strategy(authStrategy = "exe({{'CheckUserRoleStrategy'}})"),
		@Strategy(authStrategy = "exe({{'checkLoginStrategy'}})"),
			@Strategy(authStrategy = "exe({{'checkLoginPasswordStrategy'}})") })
	@RequestMapping(value = "/residence-audit", method = RequestMethod.GET)
	@Transactional
	public void residenceAudit(@AuthForHeader AuthContext context, @RequestParam("id") Integer id,
			@RequestParam("auditStatus") AuditStatus auditStatus, @RequestParam("auditMessageId") String auditMessageId,
			@RequestParam("auditMessage") String auditMessage) {
		int adminId = context.getLoginSession().getUserId();
		// 1.5.1
		// *************************
		// 设置邮件信息
		String sendMessage = "";
		if (AuditStatus.OK.equals(auditStatus)) {
			sendMessage = environmentContxt.getMsg(MessageConst.ADDRESS_VERIFY_SUCCESS_MESSAGE, auditMessage);
		}
		if (AuditStatus.FAIL.equals(auditStatus)) {
			sendMessage = environmentContxt.getMsg(MessageConst.ADDRESS_VERIFY_FAIL_MESSAGE, auditMessage);
		}
		// 居住认证update
		authenticationFacade.auditUserLevel1To2(adminId, id, auditStatus, auditMessageId, auditMessage, sendMessage);

	}

}
