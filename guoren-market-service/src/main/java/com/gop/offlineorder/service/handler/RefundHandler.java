package com.gop.offlineorder.service.handler;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.gop.asset.dto.AssetOperationDto;
import com.gop.code.consts.OfflineOrderConst;
import com.gop.code.consts.PushTradeMessageConst;
import com.gop.common.Environment;
import com.gop.common.SmsMessageService;
import com.gop.config.SmsMessageConfig;
import com.gop.domain.TokenOrder;
import com.gop.domain.enums.OperationType;
import com.gop.domain.enums.TokenOrderState;
import com.gop.exception.AppException;
import com.gop.financecheck.enums.AccountClass;
import com.gop.financecheck.enums.AccountSubject;
import com.gop.financecheck.enums.BusinessSubject;
import com.gop.offlineorder.service.TokenOrderService;
import com.gop.offlineorder.service.TokenOrderStateHandler;
import com.gop.user.facade.UserFacade;
import com.gop.util.DateUtils;

@Service("refundHandler")
public class RefundHandler implements TokenOrderStateHandler {
	@Autowired
	private TokenOrderService tokenOrderService;
	@Autowired
	private SmsMessageService smsMessageService;
	@Autowired
	UserFacade userFacade;
	@Autowired
	Environment environmentContxt;

	@Override
	public TokenOrderState getHandlerState() {
		// TODO Auto-generated method stub
		return TokenOrderState.REFUND;
	}

	@Override
	@Transactional
	public void HandlerTokenOrderState(int uid, TokenOrderState changeState, TokenOrder tokenOrder) {
		if (!tokenOrder.getState().equals(TokenOrderState.PAID)) {
			throw new AppException(OfflineOrderConst.TOKEN_ORDER_STATE_ERROR);
		}
		if (uid != tokenOrder.getSellUid().intValue()) {
			throw new AppException(OfflineOrderConst.TOKEN_ORDER_STATE_ERROR);
		}
		tokenOrder.setState(TokenOrderState.REFUND);
		tokenOrderService.updateTokenOrderStatus(tokenOrder.getId(), uid, OperationType.SELL, TokenOrderState.PAID,
				TokenOrderState.REFUND);

		String buyRequestno = tokenOrder.getBuyOrderRequestNo();
		AssetOperationDto assetOperationDto = new AssetOperationDto();
		assetOperationDto.setAccountClass(AccountClass.ASSET);
		assetOperationDto.setBusinessSubject(BusinessSubject.PUSH_RETURN);
		assetOperationDto.setAccountSubject(AccountSubject.UNKNOWN);
		assetOperationDto.setAmount(tokenOrder.getMoney());
		assetOperationDto.setAssetCode(tokenOrder.getTradeAsset());
		assetOperationDto.setLoanAmount(BigDecimal.ZERO);
		assetOperationDto.setLockAmount(BigDecimal.ZERO);
		assetOperationDto.setMemo("线下交易退回");
		assetOperationDto.setRequestNo(buyRequestno);
		assetOperationDto.setUid(tokenOrder.getBuyUid());

		String sellMessage = environmentContxt.getMsg(PushTradeMessageConst.OFFLINE_SELL_CNACEL,
				DateUtils.formatDate(tokenOrder.getCreateDate()), tokenOrder.getOrderNo());
		String buyMessage = environmentContxt.getMsg(PushTradeMessageConst.OFFLINE_BUY_CNACEL, tokenOrder.getOrderNo());
		smsMessageService.sendPhoneMessage(userFacade.getUser(tokenOrder.getSellUid()).getMobile(), sellMessage);
		smsMessageService.sendPhoneMessage(userFacade.getUser(tokenOrder.getBuyUid()).getMobile(), buyMessage);
	}

}
