package com.gop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gop.domain.TokenOrderLog;

public interface TokenOrderLogMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(TokenOrderLog record);

	int insertSelective(TokenOrderLog record);

	TokenOrderLog selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(TokenOrderLog record);

	int updateByPrimaryKey(TokenOrderLog record);

	List<TokenOrderLog> getTokenOrderLogByTokenOrderId(@Param("tokenOrderId") int tokenOrderId);
}