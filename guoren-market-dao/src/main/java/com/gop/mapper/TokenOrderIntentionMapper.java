package com.gop.mapper;

import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.Page;
import com.gop.domain.TokenOrderIntention;
import com.gop.domain.enums.TokenOrderIntentionState;

public interface TokenOrderIntentionMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(TokenOrderIntention record);

	int insertSelective(TokenOrderIntention record);

	TokenOrderIntention selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(TokenOrderIntention record);

	int updateByPrimaryKey(TokenOrderIntention record);

	Page<TokenOrderIntention> getTokenOrderIntentionByAssetName(@Param("assetName") String assetName);

	void updateUserComplete(@Param("sellUid") Integer sellUid);

	Page<TokenOrderIntention> getTokenOrderIntention(@Param("uid") Integer uid,
			@Param("status") TokenOrderIntentionState status, @Param("assetName") String assetName);
}