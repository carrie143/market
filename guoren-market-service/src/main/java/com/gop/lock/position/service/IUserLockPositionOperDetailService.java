package com.gop.lock.position.service;

import com.gop.domain.UserLockPositionOperDetail;

import java.util.List;

public interface IUserLockPositionOperDetailService {

    List<UserLockPositionOperDetail> getUserOperationList(Integer userId,String assetCode, Integer start, Integer pageSize);

    Integer countUserOperation(Integer userId);
    
    void addUserLockPositionOperDetail(UserLockPositionOperDetail userLockPositionOperDetail);
}
