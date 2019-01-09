package com.gop.lock.position.service.impl;

import com.gop.domain.UserLockPositionReward;
import com.gop.domain.enums.UserLockPositionRewardStatus;
import com.gop.lock.position.service.IUserLockPositionRewardService;
import com.gop.mapper.UserLockPositionRewardMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserLockPositionRewardServiceImpl implements IUserLockPositionRewardService{

    @Autowired
    private UserLockPositionRewardMapper userLockPositionRewardMapper;

    @Override
    public int insertSelective(UserLockPositionReward record) {
        return userLockPositionRewardMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKeySelective(UserLockPositionReward record) {
        return userLockPositionRewardMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public UserLockPositionReward selectByUserIdAndTime(UserLockPositionReward userLockPositionReward) {
        return userLockPositionRewardMapper.selectByUserIdAndTime(userLockPositionReward);
    }

    @Override
    public BigDecimal getTotalRewardByUserId(Integer userId,String coinType) {
        return userLockPositionRewardMapper.getTotalRewardByUserId(userId,coinType);
    }

    @Override
    public List<UserLockPositionReward> getRewardList(String coinType, Integer start, Integer pageSize,UserLockPositionRewardStatus status) {
        return userLockPositionRewardMapper.getRewardList(coinType, start, pageSize, status);
    }

    @Override
    public Integer countByCoinType(String coinType,UserLockPositionRewardStatus status) {
        return userLockPositionRewardMapper.countByCoinType(coinType,status);
    }

    @Override
    public List<UserLockPositionReward> getRewardByStatus(UserLockPositionRewardStatus status) {
        return userLockPositionRewardMapper.getRewardByStatus(status);
    }

	@Override
	public List<UserLockPositionReward> getRewardByYearAndMonth(String assetCode, Integer year, Integer month) {
		return userLockPositionRewardMapper.getRewardByYearAndMonth(assetCode, year, month);
	}

	@Override
	public int updateRewardStatusById(Integer id, UserLockPositionRewardStatus beginStatus,
			UserLockPositionRewardStatus endStatus) {
		return userLockPositionRewardMapper.updateRewardStatusById(id,beginStatus,endStatus);
	}
}
