package com.gop.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.Page;
import com.gop.domain.TokenOrder;
import com.gop.domain.enums.TokenOrderState;
import com.gop.mapper.dto.OrderRankOfCoinsNumDto;
import com.gop.mapper.dto.OrderRankOfLastBoundDto;
import com.gop.mapper.dto.OrderRankOfSellDto;

public interface TokenOrderMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(TokenOrder record);

	int insertSelective(TokenOrder record);

	TokenOrder selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(TokenOrder record);

	int updateByPrimaryKey(TokenOrder record);

	int countSuccessTokenOrder(@Param("uid") int sellUid);

	Page<TokenOrder> getOrderForSellByPage(@Param("sellUid") int sellUid,
			@Param("tokenOrderStatuss") List<TokenOrderState> tokenOrderStatuss);

	Page<TokenOrder> getOrderForBuyByPage(@Param("buyUid") int buyUid,
			@Param("tokenOrderStatuss") List<TokenOrderState> tokenOrderStatuss);

	int blindUid(@Param("orderId") int orderId, @Param("buyUid") int userId, @Param("bundDate") Date date);

	int updateTokenOrderStatus(@Param("orderId") int orderId,
			@Param("tokenOrderStateFrom") TokenOrderState tokenOrderStateFrom,
			@Param("tokenOrderStateTo") TokenOrderState tokenOrderStateTo);

	/**
	 * 查询成交笔数排名
	 * @return
	 */
	List<OrderRankOfSellDto> getRankOfSell();
	
	/**
	 * 查询成交币种数量排名
	 * @return
	 */
	List<OrderRankOfCoinsNumDto> getRankOfCoinsTradeNum();

	/**
	 * 查询最新成交时间倒叙排名
	 * @return
	 */
	List<OrderRankOfLastBoundDto> getRankOfLastBound();
	
	
}