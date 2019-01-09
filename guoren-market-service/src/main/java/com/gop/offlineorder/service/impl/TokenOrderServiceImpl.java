package com.gop.offlineorder.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gop.code.consts.OfflineOrderConst;
import com.gop.domain.TokenOrder;
import com.gop.domain.TokenOrderLog;
import com.gop.domain.enums.OperationType;
import com.gop.domain.enums.TokenOrderState;
import com.gop.exception.AppException;
import com.gop.mapper.TokenOrderLogMapper;
import com.gop.mapper.TokenOrderMapper;
import com.gop.mapper.dto.OrderRankOfLastBoundDto;
import com.gop.mode.vo.PageModel;
import com.gop.offlineorder.service.TokenOrderService;
import com.gop.offlineorder.service.TokenOrderStateContext;
import com.gop.offlineorder.service.TokenOrderStateHandler;

@Service
public class TokenOrderServiceImpl implements TokenOrderService {

	@Autowired
	private TokenOrderMapper tokenOrderMapper;

	@Autowired
	private TokenOrderLogMapper tokenOrderLogMapper;

	@Autowired
	TokenOrderStateContext context;

	@Override
	public int getSuccessOrderByUid(int sellUid) {

		return tokenOrderMapper.countSuccessTokenOrder(sellUid);
	}

	@Override
	public int createTokenOrder(TokenOrder tokenOrder) {

		tokenOrderMapper.insertSelective(tokenOrder);
		return tokenOrder.getId();
	}

	@Transactional
	public void updateTokenOrderStatus(int uid, int tokenOrderId, TokenOrderState tokenOrderStatus) {

		TokenOrderStateHandler tokenOrderStateHandler = context.getTokenOrderStateHandler(tokenOrderStatus);
		TokenOrder tokenOrder = tokenOrderMapper.selectByPrimaryKey(tokenOrderId);
		if (null == tokenOrder) {
			throw new AppException(OfflineOrderConst.TOKEN_ORDER_NOT_FOUND);
		}
		tokenOrderStateHandler.HandlerTokenOrderState(uid, tokenOrderStatus, tokenOrder);
	}

	@Override
	public Page<TokenOrder> getOrderForSellByPage(int sellUid, List<TokenOrderState> tokenOrderStatuss, int pageNo,
			int pageSize) {

		PageHelper.startPage(pageNo, pageSize);
		PageHelper.orderBy("id desc");
		return tokenOrderMapper.getOrderForSellByPage(sellUid, tokenOrderStatuss);
	}

	@Override
	public Page<TokenOrder> getOrderForBuyByPage(int buyUid, List<TokenOrderState> tokenOrderStatuss, int pageNo,
			int pageSize) {
		PageHelper.startPage(pageNo, pageSize);
		PageHelper.orderBy("bund_time desc");
		return tokenOrderMapper.getOrderForBuyByPage(buyUid, tokenOrderStatuss);
	}

	@Transactional
	public boolean bloundBuyUid(int tokenOrderId, Integer userId) {
		int flag = tokenOrderMapper.blindUid(tokenOrderId, userId, new Date());
		
		return flag > 0 ? true : false;
	}

	@Override
	public TokenOrder getTokenOrder(int id) {

		return tokenOrderMapper.selectByPrimaryKey(id);
	}

	@Override
	@Transactional
	public void payTokenOrder(String address, Integer id) {
		TokenOrder tokenOrder = tokenOrderMapper.selectByPrimaryKey(id);
		if (tokenOrder == null) {
			throw new AppException(OfflineOrderConst.TOKEN_ORDER_NOT_FOUND);

		}
		tokenOrder.setToAddress(address);
		tokenOrderMapper.updateByPrimaryKeySelective(tokenOrder);
		updateTokenOrderStatus(tokenOrder.getBuyUid(), tokenOrder.getId(), TokenOrderState.PAID);

	}

	@Override
	@Transactional
	public void updateTokenOrderStatus(int orderId, int uid, OperationType operationType,
			TokenOrderState tokenOrderStateFrom, TokenOrderState tokenOrderStateTo) {

		TokenOrderLog tkoenOrderLog = new TokenOrderLog();
		tkoenOrderLog.setChangeStateFrom(tokenOrderStateFrom);
		tkoenOrderLog.setChangeStateTo(tokenOrderStateTo);
		tkoenOrderLog.setOperationType(operationType);
		tkoenOrderLog.setCreateTime(new Date());
		tkoenOrderLog.setUpdatetime(new Date());
		tkoenOrderLog.setOperationUid(uid);
		tkoenOrderLog.setTokenOrderId(orderId);
		tokenOrderLogMapper.insertSelective(tkoenOrderLog);
		int num = tokenOrderMapper.updateTokenOrderStatus(orderId, tokenOrderStateFrom, tokenOrderStateTo);
		if (num == 0) {
			throw new AppException(OfflineOrderConst.TOKEN_ORDER_STATE_CHANGED, "订单状态已经改变");
		}

	}

	@Override
	public List<TokenOrderLog> getTokenOrderLog(int tokenOrderId) {

		return tokenOrderLogMapper.getTokenOrderLogByTokenOrderId(tokenOrderId);
	}
	
	@Override
	public PageModel<OrderRankOfLastBoundDto> getRankOfLastBound(Integer pageNo,Integer pageSize) {
		PageHelper.startPage(pageNo, pageSize);
		PageHelper.orderBy("update_date DESC");
		PageInfo<OrderRankOfLastBoundDto> lst = new PageInfo<>(tokenOrderMapper.getRankOfLastBound());

		PageModel<OrderRankOfLastBoundDto> pageModel = new PageModel<>();
		pageModel.setPageNo(pageNo);
		pageModel.setPageNum(lst.getPages());
		pageModel.setPageSize(pageSize);
		pageModel.setTotal(lst.getTotal());
		pageModel.setList(lst.getList());
//		return  tokenOrderMapper.getRankOfLastBound();
		return pageModel;
	}

}
