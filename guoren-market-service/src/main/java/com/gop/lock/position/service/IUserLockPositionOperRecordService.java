package com.gop.lock.position.service;

import com.gop.domain.UserLockPositionOperRecord;
import com.gop.domain.enums.UserLockPositionStatus;

import java.math.BigDecimal;
import java.util.List;

public interface IUserLockPositionOperRecordService {

    UserLockPositionOperRecord selectByPrimaryKey(Integer id);

    List<UserLockPositionOperRecord> selectByUserId(Integer userId);

    
    void addUserLockPositionOperRecord(UserLockPositionOperRecord userLockPositionOperRecord);


    /**
     * 获取上月应该发放奖励的记录
     */
    List<UserLockPositionOperRecord> getLastMonthRewardList(UserLockPositionOperRecord record);

    /**
     * 获取截止到某个时间的某个币种的锁仓总量
     */
    BigDecimal getAllAmount(UserLockPositionOperRecord record);
    
    int updateLockPositionOperRecordStatusByUidAndAssetCode(Integer uid,String assetCode,
    		UserLockPositionStatus beginStatus,UserLockPositionStatus endStatus);
}
