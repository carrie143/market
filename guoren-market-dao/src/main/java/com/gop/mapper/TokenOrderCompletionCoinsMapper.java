package com.gop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gop.domain.TokenOrderCompletionCoins;
import com.gop.mapper.dto.OrderRankOfCoinsNumDto;

public interface TokenOrderCompletionCoinsMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(TokenOrderCompletionCoins record);

	int insertSelective(TokenOrderCompletionCoins record);

	TokenOrderCompletionCoins selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(TokenOrderCompletionCoins record);

	int updateByPrimaryKey(TokenOrderCompletionCoins record);

	TokenOrderCompletionCoins selectByAssetName(String assetName);

	List<OrderRankOfCoinsNumDto> getRankOfCoinsNum();

	int saveOrIncrOrderCompleted(@Param("assetName") String assetName);
}