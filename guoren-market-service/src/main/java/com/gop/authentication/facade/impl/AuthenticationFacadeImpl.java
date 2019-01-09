package com.gop.authentication.facade.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gop.authentication.dto.IdentificationDto;
import com.google.common.base.Strings;
import com.gop.authentication.dto.UserAuthLevel0Dto;
import com.gop.authentication.dto.UserBasicInfoDto;
import com.gop.authentication.dto.UserIdentificationDto;
import com.gop.authentication.facade.AuthenticationFacade;
import com.gop.authentication.service.UserBasicInfoService;
import com.gop.authentication.service.UserIdentificationService;
import com.gop.authentication.service.UserResidenceService;
import com.gop.code.consts.CommonCodeConst;
import com.gop.code.consts.UserCodeConst;
import com.gop.common.SmsMessageService;
import com.gop.domain.User;
import com.gop.domain.UserBasicInfo;
import com.gop.domain.UserIdentification;
import com.gop.domain.UserResidence;
import com.gop.domain.enums.AuditDealStatus;
import com.gop.domain.enums.AuditStatus;
import com.gop.domain.enums.AuthLevel;
import com.gop.domain.enums.UserMessageCategory;
import com.gop.exception.AppException;
import com.gop.mode.vo.PageModel;
import com.gop.user.dto.UserSimpleInfoDto;
import com.gop.user.facade.UserFacade;
import com.gop.user.service.UserMessageService;
import com.gop.user.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Service("AuthenticationFacadeImpl")
@Slf4j
public class AuthenticationFacadeImpl implements AuthenticationFacade {

	@Autowired
	private UserBasicInfoService userBasicInfoService;

	@Autowired
	private UserIdentificationService userIdentificationService;

	@Autowired
	private UserResidenceService userResidenceService;
	@Autowired
	private UserService userService;

	@Autowired
	private UserFacade userFacade;

	@Autowired
	private SmsMessageService smsMessageService;

	@Autowired
	private UserMessageService userMessageService;

	@Override
	public void commitUserLevel1Info(UserAuthLevel0Dto userAuthLevel0Dto) {
		// TODO 构造domian
		IdentificationDto identificationDto = userAuthLevel0Dto.getIdentificationDto();
		UserBasicInfoDto userBasicInfoDto = userAuthLevel0Dto.getUserBasicInfoDto();

		UserBasicInfo userBasicInfo = new UserBasicInfo();
		userBasicInfo.setBirthday(userBasicInfoDto.getBirthday());
		userBasicInfo.setCountry(userBasicInfoDto.getCountry());
		userBasicInfo.setCountryId(userBasicInfoDto.getCountryId());
		userBasicInfo.setFirstName(userBasicInfoDto.getFirstName());
		userBasicInfo.setMiddleName(userBasicInfoDto.getMiddleName());
		userBasicInfo.setLastName(userBasicInfoDto.getLastName());
		userBasicInfo.setGender(userBasicInfoDto.getGender());
		userBasicInfo.setUid(userBasicInfoDto.getUid());

		// dto提取userIdentification
		UserIdentification userIdentification = new UserIdentification();
		userIdentification.setUid(identificationDto.getUid());
		userIdentification.setCountryId(identificationDto.getCountryId());
		userIdentification.setCountry(identificationDto.getCountry());
		userIdentification.setCardType(identificationDto.getCardType());
		userIdentification.setCardNo(identificationDto.getCardNo());
		userIdentification.setExpiredDate(identificationDto.getExpiredDate());
		userIdentification.setCardPhoto(identificationDto.getCardPhoto());
		userIdentification.setCardHandhold(identificationDto.getCardHandhold());
		userIdentification.setCardTranslate(identificationDto.getCardTranslate());
		// 因为数据库不能为null,所以此处设置为0
		userIdentification.setAuditUid(0);
		userIdentification.setAuditMessageId("0");
		userIdentification.setAuditMessage("");
		userIdentification.setFullName(
				userBasicInfo.getFirstName() + " " + userBasicInfo.getMiddleName() + " " + userBasicInfo.getLastName());

		// 只有用户达到level0之后才可以升级
		User user = userService.getUserByUid(identificationDto.getUid());
		// 更新userIdentification表中的fullname
		AuthLevel userAuthLevel = user.getAuthLevel();
		if (null != userAuthLevel && AuthLevel.LEVEL0.equals(userAuthLevel)) {
			userIdentificationService.insertUserIdentificationAndUserBasicInfo(userBasicInfo, userIdentification);
		} else {
//			log.error("用户authlevel不是0等级");
			throw new AppException(CommonCodeConst.FIELD_ERROR);
		}
	}

	@Override
	public void commUserLevel2Info(UserResidence userResidence) {
		// 只有用户达到level1之后才可以升级
		User user = userService.getUserByUid(userResidence.getUid());
		AuthLevel userAuthLevel = user.getAuthLevel();
		// 非null且是认证等级为1
		if (null != userAuthLevel && AuthLevel.LEVEL1.equals(userAuthLevel)) {
			userResidence.setFullName(user.getFullname());
			userResidenceService.insertUserResidence(userResidence);
		} else {
//			log.error("用户authlevel不是1等级");
			throw new AppException(CommonCodeConst.FIELD_ERROR);
		}

	}

	@Override
	public PageModel<UserIdentification> getUserIdentificationUnverify(Integer uid, int pageNo, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageModel<UserIdentification> getUserIdentificationverifyed(Integer uid, int pageNo, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserIdentificationDto getUserIdentificationInfo(Integer uid) {
		//获取注册用户信息,默认为已经通过审核
		UserIdentification identification = userIdentificationService.getUserIdentificationInfoWtihAuditStatus(uid,AuditStatus.OK);
		if (null != identification) {
			UserIdentificationDto userIdentificationDto = new UserIdentificationDto();
			userIdentificationDto.setCardNo(identification.getCardNo());
			userIdentificationDto.setCardType(identification.getCardType());
			return userIdentificationDto;
		}
		return null;
	}

	@Override
	public void checkPassport(Integer userId, String passort) {
		if (Strings.isNullOrEmpty(passort)) {
			throw new AppException(UserCodeConst.IDCARD_INVALID);
		}
		UserIdentification userIdentification = userIdentificationService.getLastUserIdentificationInfo(userId);
		if (userIdentification == null || !userIdentification.getAuditStatus().equals(AuditStatus.OK)) {
			throw new AppException(UserCodeConst.IDCARD_INVALID);
		}
		if (!passort.equals(userIdentification.getCardNo())) {
			throw new AppException(UserCodeConst.IDCARD_INVALID);
		}
	}

	@Override
	@Transactional
	public void auditUserLevel0To1(int adminId, Integer id, AuditStatus auditStatus, String auditMessageId,
			String auditMessage, String sendMessage) {
		// 1.5.1版本
		// 获取认证信息当前状态并逻辑判断
		UserIdentification userIdentificationInfo = userIdentificationService.getserIdentificationById(id);
		Integer uid = userIdentificationInfo.getUid();
		UserBasicInfo basicInfo = userBasicInfoService.getBasicInfoByUid(uid);
		String userFullName = basicInfo.getFirstName() + " " + basicInfo.getMiddleName() + " " + basicInfo.getLastName();
		// 数据库必须存在并且为init待审核状态
		if (null == userIdentificationInfo || !AuditStatus.INIT.equals(userIdentificationInfo.getAuditStatus())) {
//			log.error("user_identification表不存在id为{}的记录!", id);
			throw new AppException(CommonCodeConst.FIELD_ERROR, null);
		}
		// 不能已认证过
		if (AuditDealStatus.FINISH.equals(userIdentificationInfo.getStatus())) {
//			log.info("用户认证等级核对重复认证");
			throw new AppException(CommonCodeConst.FIELD_ERROR, null);
		}
		// 状态必须是0
		User userByUid = userService.getUserByUid(uid);
		if (!AuthLevel.LEVEL0.equals(userByUid.getAuthLevel())) {
//			log.error("用户认证等级核对异常,应为level0");
			throw new AppException(UserCodeConst.CERTIFICATION_NO_MATCH);
		}
		// 根据认证状态更新用户相应信息(待审核状态变换:status与auditstatus,是否完成,审核意见内容,用户表中的全名,等级认证等)
		// 更新身份认证审核
		// 除了基本信息以外的两个认证是有历史记录的,默认即便此次认证失败,但是基础信息已被更新
		// 此处更新对象使用查询更新,不使用new对象
		userIdentificationInfo.setAuditStatus(auditStatus);
		userIdentificationInfo.setAuditMessageId(auditMessageId);
		userIdentificationInfo.setAuditMessage(auditMessage);
		userIdentificationInfo.setAuditUid(adminId);
		userIdentificationInfo.setAuditDate(new Date());
		// 判断审核结果,如果通过则将status设为finish(数据库中status与auditstatus的init容易歧义,应注意)
		userIdentificationInfo.setStatus(AuditDealStatus.FINISH);
		// 更新用户认证
		userIdentificationService.updateUserIdentification(userIdentificationInfo);
		// 升级用户等级
		if (AuditStatus.OK.equals(auditStatus)) {
			userFacade.updateAuthStatus(uid, AuthLevel.LEVEL1);
			userFacade.updateUserInfo(uid, userFullName);
		}
		// 更新用户全名
		// 根据评审结果发送相应邮件(失败邮件与通过邮件)
		UserSimpleInfoDto userSimpleInfoDto = userFacade.getUserInfoByUid(uid);
		// 发送邮件(由于需要EnvironmentContxt获取message,使用controller处理好的信息)
		smsMessageService.sendEmailMessage(userSimpleInfoDto.getUserAccount(), sendMessage);
		userMessageService.insertMessage(uid, sendMessage, UserMessageCategory.SECURITY);

	}

	@Override
	@Transactional
	public void auditUserLevel1To2(int adminId, Integer id, AuditStatus auditStatus, String auditMessageId,
			String auditMessage, String sendMessage) {
		// 1.5.1版本
		// 获取用户地址认证信息当前状态并逻辑判断
		UserResidence userResidenceInfo = userResidenceService.getUserResidenceInfoById(id);
		Integer uid = userResidenceInfo.getUid();
		// 数据库必须存在并且为init待审核状态
		if (null == userResidenceInfo || !AuditStatus.INIT.equals(userResidenceInfo.getAuditStatus())) {
//			log.info("user_residence表不存在id为{}的记录!", id);
			throw new AppException(CommonCodeConst.FIELD_ERROR, null);
		}
		// 不能已认证过
		if (AuditDealStatus.FINISH.equals(userResidenceInfo.getStatus())) {
//			log.info("用户认证等级核对重复认证");
			throw new AppException(CommonCodeConst.FIELD_ERROR, null);
		}
		// 状态必须是1
		User userByUid = userService.getUserByUid(uid);
		if (!AuthLevel.LEVEL1.equals(userByUid.getAuthLevel())) {
//			log.info("用户认证等级核对异常,应为level1");
			throw new AppException(UserCodeConst.CERTIFICATION_NO_MATCH);
		}
		// 根据认证状态更新用户相应信息(待审核状态变换:status与auditstatus,是否完成,审核意见内容,等级认证等)
		// 更新地址认证审核
		// 此处更新对象使用查询更新,不使用new对象
		userResidenceInfo.setAuditStatus(auditStatus);
		userResidenceInfo.setAuditMessageId(auditMessageId);
		userResidenceInfo.setAuditMessage(auditMessage);
		userResidenceInfo.setAuditUid(adminId);
		userResidenceInfo.setAuditDate(new Date());
		// 判断审核结果,如果通过则将status设为finish(数据库中status与auditstatus的init容易歧义,应注意)
		userResidenceInfo.setStatus(AuditDealStatus.FINISH);
		// 更新用户认证
		userResidenceService.updateUserResidenc(userResidenceInfo);
		// 升级用户等级到level2
		if (AuditStatus.OK.equals(auditStatus)) {
			userFacade.updateAuthStatus(uid, AuthLevel.LEVEL2);
		}
		// 根据评审结果发送相应邮件(失败邮件与通过邮件)
		UserSimpleInfoDto userSimpleInfoDto = userFacade.getUserInfoByUid(uid);
		// 发送邮件(由于需要EnvironmentContxt获取message,使用controller处理好的信息)
		smsMessageService.sendEmailMessage(userSimpleInfoDto.getUserAccount(), sendMessage);
		userMessageService.insertMessage(uid, sendMessage, UserMessageCategory.SECURITY);

	}

}