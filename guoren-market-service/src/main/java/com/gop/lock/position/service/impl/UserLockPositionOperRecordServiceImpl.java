package com.gop.lock.position.service.impl;

import com.gop.domain.UserLockPositionOperRecord;
import com.gop.domain.enums.UserLockPositionStatus;
import com.gop.lock.position.service.IUserLockPositionOperRecordService;
import com.gop.mapper.UserLockPositionOperRecordMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserLockPositionOperRecordServiceImpl implements IUserLockPositionOperRecordService{

    @Autowired
    private UserLockPositionOperRecordMapper userLockPositionOperRecordMapper;

    @Override
    public UserLockPositionOperRecord selectByPrimaryKey(Integer id) {
        return userLockPositionOperRecordMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<UserLockPositionOperRecord> selectByUserId(Integer userId) {
        return userLockPositionOperRecordMapper.selectByUserId(userId);
    }


	@Override
	public void addUserLockPositionOperRecord(UserLockPositionOperRecord userLockPositionOperRecord) {
		userLockPositionOperRecordMapper.addUserLockPositionOperRecord(userLockPositionOperRecord);
	}

    @Override
    public List<UserLockPositionOperRecord> getLastMonthRewardList(UserLockPositionOperRecord record) {
        return userLockPositionOperRecordMapper.getLastMonthRewardList(record);
    }

    @Override
    public BigDecimal getAllAmount(UserLockPositionOperRecord record) {
        return userLockPositionOperRecordMapper.getAllAmount(record);
    }

	@Override
	public int updateLockPositionOperRecordStatusByUidAndAssetCode(Integer uid, String assetCode,
			UserLockPositionStatus beginStatus, UserLockPositionStatus endStatus) {
		return userLockPositionOperRecordMapper.updateLockPositionOperRecordStatusByUidAndAssetCode(uid,assetCode,beginStatus,endStatus);
	}
}
