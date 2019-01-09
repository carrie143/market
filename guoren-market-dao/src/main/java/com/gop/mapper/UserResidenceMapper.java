package com.gop.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gop.domain.UserResidence;
import com.gop.domain.enums.AuditDealStatus;
import com.gop.domain.enums.AuditStatus;

public interface UserResidenceMapper {

	// 插入数据
	int insert(UserResidence userResidence);
	
	int insertSelective(UserResidence userResidence);
	
	UserResidence getLastResidenceInfoByUid(Integer user_id);

	List<UserResidence> getResidenceInfoList(@Param("uid") Integer uid, @Param("status") AuditDealStatus status);

	UserResidence getResidenceInfoById(Integer id);

	void updateAudit(UserResidence info);

	List<UserResidence> getResidenceHistoryList(Integer userId);
	
	int getResidenceHistoryCountList(Integer userId);
	
	int updateByPrimaryKeySelective(UserResidence info);

	List<UserResidence> getIdentityHistoryListByLimitNo(@Param("uid")Integer uid,@Param("limitNo") Integer limitNo);

	Integer getAmountOfResidenceWithStatus(AuditStatus status);

	UserResidence getUserByUidAndStatusAndAuditStatus(@Param("uid")Integer uid, @Param("status")AuditDealStatus status, @Param("auditStatus")AuditStatus auditStatus);

	int countUserLevel(@Param("date") Date date);
}