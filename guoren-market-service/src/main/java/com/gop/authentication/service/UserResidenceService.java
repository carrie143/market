package com.gop.authentication.service;

import java.util.Date;
import java.util.List;

import com.gop.domain.UserResidence;
import com.gop.domain.enums.AuditDealStatus;
import com.gop.domain.enums.AuditStatus;
import com.gop.mode.vo.PageModel;

public interface UserResidenceService {

	void insertUserResidence(UserResidence userResidence);

	UserResidence getUserResidenceInfoById(Integer uid);

	PageModel<UserResidence> getUserResidencePageModel(Integer uid, AuditDealStatus status, Integer pageNo,
			Integer pageSize);

	void updateUserResidenc(UserResidence userResidenceInfoById);

	UserResidence getLastUserResidenceInfo(Integer uid);

	List<UserResidence> getIdentityHistoryListByLimitNo(Integer uid, Integer limitNo);

	Integer getAmountOfResidenceWithStatus(AuditStatus status);

	UserResidence getUserByUidAndStatusAndAuditStatus(Integer uid, AuditDealStatus dealStatus, AuditStatus status);

	int countUserLevel(Date date);
}
