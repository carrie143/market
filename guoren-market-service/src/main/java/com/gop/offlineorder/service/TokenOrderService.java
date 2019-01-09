package com.gop.offlineorder.service;

import java.util.List;

import com.github.pagehelper.Page;
import com.gop.domain.TokenOrder;
import com.gop.domain.TokenOrderLog;
import com.gop.domain.enums.OperationType;
import com.gop.domain.enums.TokenOrderState;
import com.gop.mapper.dto.OrderRankOfLastBoundDto;
import com.gop.mode.vo.PageModel;

public interface TokenOrderService {
	// 获取成功交易次数
	public int getSuccessOrderByUid(int sellUid);

	// 创建订单
	public int createTokenOrder(TokenOrder tokenOrder);

	// 更新订单状态
	public void updateTokenOrderStatus(int uid, int tokenOrderId, TokenOrderState tokenOrderStatus);

	//  获取卖家订单
	public Page<TokenOrder> getOrderForSellByPage(int sellUid, List<TokenOrderState> tokenOrderStatuss, int pageNo,
			int pageSize);

	// 只能内部调用
	/**
	 * 
	 * @param uid操作用户id
	 * @param operationType
	 *            操作类型
	 * @param tokenOrderState
	 *            更改前状态
	 * @param tokenOrder
	 *            需要更新的order
	 */
	public void updateTokenOrderStatus(int tokenOrderId, int uid, OperationType operationType,
			TokenOrderState tokenOrderStateForm, TokenOrderState tokenOrderStateTo);

	// 获取买家订单
	public Page<TokenOrder> getOrderForBuyByPage(int buyUid, List<TokenOrderState> tokenOrderStatuss, int pageNo,
			int pageSize);

	public boolean bloundBuyUid(int tokenOrderId, Integer userId);

	public TokenOrder getTokenOrder(int id);

	public void payTokenOrder(String address, Integer id);

	public List<TokenOrderLog> getTokenOrderLog(int tokenOrderId);
	
	/**
	 * 成交时间查询倒排名分页
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	PageModel<OrderRankOfLastBoundDto> getRankOfLastBound(Integer pageNo, Integer pageSize);
	
	

}
