package com.gop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gop.domain.TokenOrderCompletionCount;
import com.gop.mapper.dto.OrderRankOfSellDto;

public interface TokenOrderCompletionCountMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(TokenOrderCompletionCount record);

	int insertSelective(TokenOrderCompletionCount record);

	TokenOrderCompletionCount selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(TokenOrderCompletionCount record);

	int updateByPrimaryKey(TokenOrderCompletionCount record);

	TokenOrderCompletionCount selectByUid(Integer uid);

	List<OrderRankOfSellDto> getRankOfSell();

	int insertOrIncrBuyCompleted(@Param("uid") int uid);

	int insertOrIncrSellCompleted(@Param("uid") int uid);

}