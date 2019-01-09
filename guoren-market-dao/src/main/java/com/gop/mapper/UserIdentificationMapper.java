package com.gop.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.gop.domain.enums.AuditDealStatus;
import com.gop.domain.enums.AuditStatus;
import com.gop.domain.enums.VerifyStatus;
import com.gop.domain.UserIdentification;

public interface UserIdentificationMapper {

    int insert(UserIdentification record);

    UserIdentification getLastIdentityInfoByUid(Integer user_id);

    //获取身份认证信息
    List<UserIdentification> getIdentityInfoList(
            @Param("userId") Integer userId,
            @Param("status") AuditDealStatus status);

    //获取身份认证信息详情
    UserIdentification getIdentityInfoById(Integer id);

    //身份认证信息审核
    void updateAudit(UserIdentification info);

    //身份认证历史信息
    List<UserIdentification> getIdentityHistoryList(Integer userId);

    List<UserIdentification> getIdentityHistoryListByLimitNo(@Param("uid") Integer uid, @Param("limitNo") Integer limitNo);

    int getIdentityHistoryCount(Integer userId);

    UserIdentification getLastUserIdentificationInfoByUid(Integer uid);

    UserIdentification getUserIdentificationInfoWtihAuditStatus(@Param("uid") Integer uid, @Param("status") AuditStatus status);

    Integer getAmountOfIdentificationWithStatus(AuditStatus status);

    UserIdentification getUserByUidAndStatusAndAuditStatus(@Param("uid") Integer uid, @Param("status") AuditDealStatus status,
                                                           @Param("auditStatus") AuditStatus auditStatus);

    int countUserLevel(@Param("date") Date date);

    List getAddUser(Map param);
    int getUserInvite(int uid);
}