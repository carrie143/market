package com.gop.authentication.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import org.apache.zookeeper.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.support.logging.Log;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gop.authentication.service.UserBasicInfoService;
import com.gop.authentication.service.UserIdentificationService;
import com.gop.code.consts.AuditCodeConst;
import com.gop.code.consts.CommonCodeConst;
import com.gop.domain.User;
import com.gop.domain.UserBasicInfo;
import com.gop.domain.UserIdentification;
import com.gop.domain.UserInfo;
import com.gop.domain.enums.AuditDealStatus;
import com.gop.domain.enums.AuditStatus;
import com.gop.domain.enums.AuditFirst;
import com.gop.domain.enums.VerifyStatus;
import com.gop.exception.AppException;
import com.gop.mapper.UserIdentificationMapper;
import com.gop.mode.vo.PageModel;
import com.gop.user.facade.UserFacade;
import com.gop.user.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserIdentificationImpl implements UserIdentificationService {

    @Autowired
    private UserIdentificationMapper userIdentificationMapper;

    @Autowired
    private UserBasicInfoService userBasicInfoService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserFacade userFacade;

    @Transactional
    public void insertUserIdentificationAndUserBasicInfo(UserBasicInfo userBasicInfo,
                                                         UserIdentification userIdentification) {
        // 保存基本信息
        userBasicInfoService.insertOrUpdate(userBasicInfo);
        // 查询身份认证记录
        UserIdentification identityInfo = userIdentificationMapper.getLastIdentityInfoByUid(userBasicInfo.getUid());
        // 校验验证状态
        if (null != identityInfo && !AuditStatus.FAIL.equals(identityInfo.getAuditStatus())) {
            // log.error("用户有待审核或者已完成审核的状态");
            throw new AppException(AuditCodeConst.UNVERIFY_EXIST, null);
        }
        // 校验是否首次
        if (identityInfo != null) {
            userIdentification.setAuditFirst(AuditFirst.NO);
        } else {
            userIdentification.setAuditFirst(AuditFirst.YES);
        }

        // 设置auditStatus为待审核
        userIdentification.setAuditStatus(AuditStatus.INIT);
        //
        userIdentification.setStatus(AuditDealStatus.INIT);
        // TODO
        userIdentification.setAuditDate(new Date());
        userIdentificationMapper.insert(userIdentification);
    }

    @Override
    public UserIdentification getserIdentificationById(Integer id) {
        try {
            UserIdentification identityInfoById = userIdentificationMapper.getIdentityInfoById(id);
            return identityInfoById;
        } catch (Exception e) {
            // log.info("根据id查询用户身份认证信息失败");
            throw new AppException(CommonCodeConst.SERVICE_ERROR);
        }

    }

    @Override
    public PageModel<UserIdentification> getUserIdentificationPageModel(Integer uid, AuditDealStatus status,
                                                                        Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        PageModel<UserIdentification> pageModel = new PageModel<>();
        try {
            PageInfo<UserIdentification> pageInfo = new PageInfo<>(
                    userIdentificationMapper.getIdentityInfoList(uid, status));
            pageModel.setPageNo(pageNo);
            pageModel.setPageNum(pageInfo.getPageNum());
            pageModel.setPageNum(pageInfo.getPages());
            pageModel.setTotal(pageInfo.getTotal());
            // pageModel.setList(pageInfo.getList());
            pageModel.setList(pageInfo.getList().stream().map(r -> {
                User user = userFacade.getUser(null != uid ? uid : r.getUid());
                String email = null != user ? user.getEmail() : "";
                r.setEmail(email);
                return r;
            }).collect(Collectors.toList()));
        } catch (Exception e) {
            // log.error("用户身份认证审核页面查询异常", e);
            throw new AppException(CommonCodeConst.SERVICE_ERROR);
        }
        return pageModel;
    }

    @Transactional
    public void updateUserIdentificationAndBasicInfo(UserBasicInfo basicInfo,
                                                     UserIdentification getserIdentificationById) {
        try {
            userBasicInfoService.insertOrUpdate(basicInfo);
            userIdentificationMapper.updateAudit(getserIdentificationById);
        } catch (Exception e) {
            // log.error("更新用户审核level0-level1失败", e);
            throw new AppException(CommonCodeConst.SERVICE_ERROR);
        }
    }

    @Override
    public void updateUserIdentification(UserIdentification getserIdentificationById) {
        try {
            userIdentificationMapper.updateAudit(getserIdentificationById);
        } catch (Exception e) {
            // log.error("更新用户审核信息失败", e);
            throw new AppException(CommonCodeConst.SERVICE_ERROR);
        }
    }

    @Override
    public UserIdentification getLastUserIdentificationInfo(Integer uid) {
        try {
            UserIdentification userIdentification = userIdentificationMapper.getLastUserIdentificationInfoByUid(uid);
            return userIdentification;
        } catch (Exception e) {
            // log.error("更新用户审核信息失败", e);
            throw new AppException(CommonCodeConst.SERVICE_ERROR);
        }
    }

    @Override
    public List<UserIdentification> getLastUserIdentificationInfoList(Integer uid) {
        try {
            List<UserIdentification> identityHistoryList = userIdentificationMapper.getIdentityHistoryList(uid);
            return identityHistoryList;
        } catch (Exception e) {
            // log.error("查询用户身份认证历史审核信息失败", e);
            throw new AppException(CommonCodeConst.SERVICE_ERROR);
        }
    }

    @Override
    public List<UserIdentification> getIdentityHistoryListByLimitNo(Integer uid, Integer limitNo) {
        try {
            List<UserIdentification> identityHistoryList = userIdentificationMapper.getIdentityHistoryListByLimitNo(uid,
                    limitNo);
            return identityHistoryList;
        } catch (Exception e) {
            // log.error("查询用户身份限定数量的认证历史审核信息失败", e);
            throw new AppException(CommonCodeConst.SERVICE_ERROR);
        }
    }

    @Override
    public UserIdentification getUserIdentificationInfoWtihAuditStatus(Integer uid, AuditStatus status) {
        try {
            UserIdentification identification = userIdentificationMapper.getUserIdentificationInfoWtihAuditStatus(uid,
                    status);
            return identification;
        } catch (Exception e) {
            // log.error("查询用户身份限定数量的认证历史审核信息失败", e);
            throw new AppException(CommonCodeConst.SERVICE_ERROR);
        }
    }

    @Override
    public Integer getAmountOfIdentificationWithStatus(AuditStatus status) {
        try {
            return userIdentificationMapper.getAmountOfIdentificationWithStatus(status);
        } catch (Exception e) {
            // log.error("查询限定状态的认证历史审核信息失败", e);
            throw new AppException(CommonCodeConst.SERVICE_ERROR);
        }
    }

    @Override
    public UserIdentification getUserByUidAndStatusAndAuditStatus(Integer uid, AuditDealStatus status,
                                                                  AuditStatus auditStatus) {
        try {
            return userIdentificationMapper.getUserByUidAndStatusAndAuditStatus(uid, status, auditStatus);
        } catch (Exception e) {
            // log.error("查询限定状态的认证历史审核信息失败", e);
            throw new AppException(CommonCodeConst.SERVICE_ERROR);
        }
    }

    @Override
    public int countUserLevel(Date date) {
        return userIdentificationMapper.countUserLevel(date);
    }

    @Override
    public List getAddUser(Map param) {
        return userIdentificationMapper.getAddUser(param);
    }

    @Override
    public int getUserInvite(int uid) {
        return userIdentificationMapper.getUserInvite(uid);
    }


}
