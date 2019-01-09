//package com.gop.certification.controller.domestic;
//
//import java.util.Date;
//import java.util.List;
//import java.util.stream.Collectors;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.github.pagehelper.PageInfo;
//import com.gop.authentication.service.VerifyService;
//import com.gop.certification.controller.dto.IdentityDto;
//import com.gop.code.consts.CommonCodeConst;
//import com.gop.code.consts.MessageConst;
//import com.gop.common.SmsMessageService;
//import com.gop.conetxt.EnvironmentContxt;
//import com.gop.config.SmsMessageConfig;
//import com.gop.domain.Administrators;
//import com.gop.domain.UserIdentification;
//import com.gop.domain.UserInfoProfile;
//import com.gop.domain.enums.AuditDealStatus;
//import com.gop.domain.enums.AuditStatus;
//import com.gop.domain.enums.AuthLevel;
//import com.gop.domain.enums.UserMessageCategory;
//import com.gop.domain.enums.VerifyStatus;
//import com.gop.exception.AppException;
//import com.gop.mode.vo.PageModel;
//import com.gop.user.dto.UserSimpleInfoDto;
//import com.gop.user.facade.UserFacade;
//import com.gop.user.service.AdministractorService;
//import com.gop.user.service.UserMessageService;
//import com.gop.web.base.annotation.AuthForHeader;
//import com.gop.web.base.auth.AuthContext;
//import com.gop.web.base.auth.annotation.Strategy;
//import com.gop.web.base.auth.annotation.Strategys;
//
//@RestController("managerCertificationController")
//@RequestMapping("/security/domestic")
//// @Api("用户中心")
//public class CertificationController {
//	@Autowired
//	private VerifyService verifyService;
//
//	@Autowired
//	private UserFacade userFacade;
//
//	
//	@Autowired
//	private AdministractorService administractorService;
//
//	@Autowired
//	private UserMessageService cmsNewsService;
//
//	@Autowired
//	private SmsMessageService smsMessageService;
//
//	@Autowired
//	private EnvironmentContxt environmentContxt;
//
//	
//	/**
//	 * 获取身份认证详情
//	 * 
//	 * @return
//	 */
//	@Strategys(strategys = { @Strategy(authStrategy = "exe({{'checkLoginStrategy'}})") })
//	@RequestMapping(value = "/identity-detail", method = RequestMethod.GET)
//	// @ApiOperation("获取身份认证详情")
//	public IdentityDto getIdentityInfoById(@AuthForHeader AuthContext context, @RequestParam("id") Integer id) {
//
//		UserIdentification userIdentification = verifyService.getIdentityInfoById(id);
//		IdentityDto dto = new IdentityDto(userIdentification);
//		UserSimpleInfoDto user = userFacade.getUserInfoByUid(userIdentification.getUid());
//		if (dto.getAuditUserId() != null && !dto.getAuditUserId().equals(0)) {
//			Administrators administrators = administractorService.getAdministractor(dto.getAuditUserId());
//			dto.setAdminName(administrators.getOpName());
//		}
//
//		dto.setName(user.getFullName());
//		dto.setPhone(user.getUserAccount());
//
//		int submitnum = verifyService.getIdentityHistoryList(userIdentification.getUid()).size();
//		dto.setSubmitNum(submitnum);
//		dto.setRefundNum(submitnum - 1 > 0 ? (submitnum - 1) : 0);
//		return dto;
//
//	}
//
//	/**
//	 * 获取身份认证信息列表
//	 * 
//	 * @param status
//	 * @param pageNo
//	 * @param pageSize
//	 * @param request
//	 * @return
//	 */
//	@Strategys(strategys = { @Strategy(authStrategy = "exe({{'checkLoginStrategy'}})") })
//	@RequestMapping(value = "identities", method = RequestMethod.GET)
//	// @ApiOperation("获取身份证认证信息列表")
//	public PageModel<IdentityDto> getIdentityInfoList(@AuthForHeader AuthContext context,
//			@RequestParam(value = "uid", required = false) Integer uid,
//			@RequestParam(value = "status", required = false) VerifyStatus status,
//			@RequestParam("pageNo") Integer pageNo, @RequestParam("pageSize") Integer pageSize) {
//
//		List<UserIdentification> list = verifyService.getIdentityInfoList(uid, status, pageNo, pageSize);
//		if (list == null) {
//			return new PageModel<>();
//
//		}
//		PageInfo<UserIdentification> pageInfo = new PageInfo<>(list);
//
//		PageModel<IdentityDto> pageMode = new PageModel<>();
//
//		pageMode.setPageNo(pageNo);
//		pageMode.setPageSize(pageSize);
//		pageMode.setPageNum(pageInfo.getPages());
//		pageMode.setTotal(pageInfo.getTotal());
//
//		List<IdentityDto> lstDtos = list.stream().map(ident -> new IdentityDto(ident)).collect(Collectors.toList());
//		lstDtos.stream().forEach(dto -> {
//			UserSimpleInfoDto user = userFacade.getUserInfoByUid(dto.getUserId());
//			if (null != dto.getAuditUserId() && !dto.getAuditUserId().equals(0)) {
//				Administrators administrators = administractorService.getAdministractor(dto.getAuditUserId());
//				dto.setAdminName(administrators.getOpName());
//			}
//
//			dto.setName(user.getFullName());
//			dto.setPhone(user.getUserAccount());
//		});
//		pageMode.setList(lstDtos);
//
//		return pageMode;
//	}
//
//	/**
//	 * 审核身份认证信息
//	 * 
//	 * @return
//	 */
//	@Strategys(strategys = { @Strategy(authStrategy = "exe({{'checkLoginStrategy'}})"),
//			@Strategy(authStrategy = "exe({{'checkPwdPasswordStrategy'}})") })
//	@RequestMapping(value = "/identity-audit", method = RequestMethod.GET)
//	// @ApiOperation("审核身份认证信息")
//	@Transactional
//	public void IdentityAuthenticationAudit(@AuthForHeader AuthContext context, @RequestParam("id") Integer id,
//			@RequestParam("auditStatus") String auditStatus, @RequestParam("auditMessageId") String auditMessageId,
//			@RequestParam("auditMessage") String auditMessage) {
//
//		int adminId = context.getLoginSession().getUserId();
//		// 获取认证信息当前状态
//		UserIdentification infoInDB = verifyService.getIdentityInfoById(id);
//		if (infoInDB == null) {
//
//			throw new AppException(CommonCodeConst.SERVICE_ERROR, null);
//		}
//		if (AuditDealStatus.FINISH.toString().equals(infoInDB.getStatus())) {
//			throw new AppException(CommonCodeConst.SERVICE_ERROR, null);
//		}
//
//		AuditStatus auditStatusBack = getAuditStatus(auditStatus);
//		UserIdentification info = new UserIdentification();
//
//		info.setId(id);
//		info.setAuditStatus(auditStatusBack.toString());
//		info.setAuditMessageId(auditMessageId);
//		info.setAuditMessage(auditMessage);
//		info.setAuditUid(adminId);
//		info.setAuditDate(new Date());
//
//		if (AuditStatus.OK.toString().equals(auditStatusBack.toString())) {// 审核状态为通过
//			// 将状态改为完成
//			info.setStatus(AuditDealStatus.FINISH.toString());
//
//			userFacade.updateAuthStatus(infoInDB.getUid(), AuthLevel.LEVEL2);
//
//			sendMail(infoInDB.getUid(), "ID", "S", null);
//		} else if (AuditStatus.FAIL.toString().equals(auditStatusBack.toString())) {// 审核结果为失败
//			// 将状态改为完成
//			info.setStatus(AuditDealStatus.FINISH.toString());
//			sendMail(infoInDB.getUid(), "ID", "F", auditMessage);
//		}
//		verifyService.auditIdentity(info);
//
//	}
//
//	// 发送实名验证通过/失败邮件
//	private void sendMail(Integer userId, String flag, String status, String msg) {
//
//		String message = null;
//		if ("ID".equals(flag)) {
//			if ("S".equals(status)) {
//				message = environmentContxt.getMsg(MessageConst.ID_VERIFY_SUCCESS_MESSAGE, msg);
//			} else if ("F".equals(status)) {
//				message = environmentContxt.getMsg(MessageConst.ID_VERIFY_FAIL_MESSAGE, msg);
//			}
//		} else {
//			if ("S".equals(status)) {
//				message = environmentContxt.getMsg(MessageConst.ADDRESS_VERIFY_SUCCESS_MESSAGE, msg);
//			} else if ("F".equals(status)) {
//				message = environmentContxt.getMsg(MessageConst.ADDRESS_VERIFY_FAIL_MESSAGE, msg);
//			}
//		}
//
//		UserSimpleInfoDto user = userFacade.getUserInfoByUid(userId);
//		smsMessageService.sendEmailMessage( user.getUserAccount(), message);
//		cmsNewsService.insertMessage(userId, message, UserMessageCategory.SECURITY);
//	}
//
//	private void saveProfile(Integer userId, String group, String index, String dataType, String key, String value,
//			Date createDate, Date updateDate) {
//
//		if (value != null && key != null) {
//			UserInfoProfile userInfoProfile = new UserInfoProfile();
//			userInfoProfile.setUid(userId);
//			userInfoProfile.setProfileGroup(group);
//			userInfoProfile.setCreateDate(createDate);
//			userInfoProfile.setUpdateDate(updateDate);
//			userInfoProfile.setDataType(dataType);
//			userInfoProfile.setProfileIndex(index);
//			userInfoProfile.setProfileKey(key);
//			userInfoProfile.setProfileValue(value);
//			verifyService.saveUserInfoProfile(userInfoProfile);
//		}
//
//	}
//
//	
//
//	private AuditStatus getAuditStatus(String auditStatus) {
//		if ("FAIL".equals(auditStatus)) {
//			return AuditStatus.FAIL;
//		} else if ("APPROVED".equals(auditStatus)) {
//			return AuditStatus.OK;
//		}
//		return null;
//	}
//
//}
