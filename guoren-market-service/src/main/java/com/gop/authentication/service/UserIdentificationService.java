package com.gop.authentication.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.gop.domain.UserBasicInfo;
import com.gop.domain.UserIdentification;
import com.gop.domain.enums.AuditDealStatus;
import com.gop.domain.enums.AuditStatus;
import com.gop.domain.enums.VerifyStatus;
import com.gop.mode.vo.PageModel;

public interface UserIdentificationService {

    void insertUserIdentificationAndUserBasicInfo(UserBasicInfo userBasicInfo, UserIdentification userIdentification);

    UserIdentification getserIdentificationById(Integer id);

    PageModel<UserIdentification> getUserIdentificationPageModel(Integer uid, AuditDealStatus status, Integer pageNo,
                                                                 Integer pageSize);

    void updateUserIdentificationAndBasicInfo(UserBasicInfo userInfoByUid, UserIdentification getserIdentificationById);

    void updateUserIdentification(UserIdentification getserIdentificationById);

    UserIdentification getLastUserIdentificationInfo(Integer uid);

    List<UserIdentification> getLastUserIdentificationInfoList(Integer uid);

    List<UserIdentification> getIdentityHistoryListByLimitNo(Integer uid, Integer limitNo);

    UserIdentification getUserIdentificationInfoWtihAuditStatus(Integer uid, AuditStatus status);

    Integer getAmountOfIdentificationWithStatus(AuditStatus status);

    UserIdentification getUserByUidAndStatusAndAuditStatus(Integer uid, AuditDealStatus status, AuditStatus auditStatus);

    int countUserLevel(Date date);

    List getAddUser(Map param);
    int getUserInvite(int uid);
}
