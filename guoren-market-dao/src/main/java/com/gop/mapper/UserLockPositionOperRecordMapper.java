package com.gop.mapper;

import com.gop.domain.UserLockPositionOperRecord;
import com.gop.domain.enums.UserLockPositionStatus;

import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface UserLockPositionOperRecordMapper {

    int insert(UserLockPositionOperRecord record);

    int insertSelective(UserLockPositionOperRecord record);

    UserLockPositionOperRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserLockPositionOperRecord record);

    int updateByPrimaryKey(UserLockPositionOperRecord record);

    List<UserLockPositionOperRecord> selectByUserId(@Param("uid") Integer userId);

    int addUserLockPositionOperRecord(UserLockPositionOperRecord userLockPositionOperRecord);

    List<UserLockPositionOperRecord> getLastMonthRewardList(UserLockPositionOperRecord record);

    BigDecimal getAllAmount(UserLockPositionOperRecord record);
    
    int updateLockPositionOperRecordStatusByUidAndAssetCode(@Param("uid") Integer uid,@Param("assetCode") String assetCode,
    		@Param("beginStatus") UserLockPositionStatus beginStatus,@Param("endStatus") UserLockPositionStatus endStatus);
}