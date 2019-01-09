package com.gop.lock.position.service;

import java.math.BigDecimal;

import com.gop.domain.UserLockPosition;


/**
 * @author yujianjian
 * @since 2017-12-22 下午3:19
 */
public interface IUserLockPositionService {

    UserLockPosition selectByUidAndAssetCode(Integer uid,String assetCode);
    
    void addUserLockPosition(UserLockPosition userLockPosition);
    
    void updateUserLockPositionAmountByUidAndAssetCode(UserLockPosition userLockPosition);
    
    void updateUserLockPositionAmountByid(Integer id,BigDecimal amount);
    
}
