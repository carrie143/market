package com.gop.mapper;

import com.gop.domain.UserRedeemActivityDetail;

public interface UserRedeemActivityDetailMapper {
	
    int insert(UserRedeemActivityDetail record);

    int insertSelective(UserRedeemActivityDetail record);

    UserRedeemActivityDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserRedeemActivityDetail record);

    int updateByPrimaryKey(UserRedeemActivityDetail record);
    
    void addUserRedeemActivityDetail(UserRedeemActivityDetail record);
    
}