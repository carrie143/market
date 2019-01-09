package com.gop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gop.domain.UserRedeemActivityConfig;
import com.gop.domain.enums.UserRedeemActivityConfigStatus;

public interface UserRedeemActivityConfigMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserRedeemActivityConfig record);

    int insertSelective(UserRedeemActivityConfig record);

    UserRedeemActivityConfig selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserRedeemActivityConfig record);

    int updateByPrimaryKey(UserRedeemActivityConfig record);

	int insertOrUpdate(UserRedeemActivityConfig config);

	List<UserRedeemActivityConfig> getConfigListByStatus(@Param("status")UserRedeemActivityConfigStatus status);
}