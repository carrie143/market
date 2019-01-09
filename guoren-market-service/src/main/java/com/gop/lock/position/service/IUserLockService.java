package com.gop.lock.position.service;

import java.math.BigDecimal;

import com.gop.domain.UserLockPositionOperDetail;
import com.gop.domain.UserLockPositionReward;
import com.gop.lock.position.service.dto.UserLockPositionBasicDto;
import com.gop.mode.vo.PageModel;

/**
 * @author yujianjian
 * @since 2017-12-22 下午3:33
 */
public interface IUserLockService {


    /**
     * 获取用户展示信息
     */
    UserLockPositionBasicDto getUserLockInfo(Integer userId, String coinType);

    /**
     * 所有人奖励列表接口
     */
    PageModel<UserLockPositionReward> getRewardList(String coinType,Integer page,Integer pageSize);

    /**
     * 用户操作记录列表
     */
    PageModel<UserLockPositionOperDetail> getUserOperationList(Integer userId,String assetCode, Integer page, Integer pageSize);


    /**
     * 用户锁仓
     */
    public void userLockPosition(Integer uid,String assetCode,BigDecimal amount);
    
    /**
     * 用户解锁仓
     */
    public void userUnLockPosition(Integer uid,String assetCode);
    
    /**
     * 清算用户锁仓奖励
     */
    public void calculateUserLockPositionReward(String assetCode,Integer rewardYear,Integer rewardMonth);
    
    /**
     * 发放用户锁仓奖励
     */
    public void grantUserLockPositionReward(String assetCode,Integer rewardYear,Integer rewardMonth);
}
