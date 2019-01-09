package com.gop.offlineorder.service.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gop.code.consts.OfflineOrderConst;
import com.gop.code.consts.PushTradeMessageConst;
import com.gop.common.Environment;
import com.gop.common.SmsMessageService;
import com.gop.config.SmsMessageConfig;
import com.gop.domain.TokenOrder;
import com.gop.domain.enums.OperationType;
import com.gop.domain.enums.TokenOrderState;
import com.gop.exception.AppException;
import com.gop.offlineorder.service.TokenOrderService;
import com.gop.offlineorder.service.TokenOrderStateHandler;
import com.gop.user.facade.UserFacade;
import com.gop.util.DateUtils;

@Service("shippedHandler")
public class ShippedHandler implements TokenOrderStateHandler {
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
		return TokenOrderState.SHIPPED;
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
		tokenOrder.setState(TokenOrderState.SHIPPED);
		tokenOrderService.updateTokenOrderStatus(tokenOrder.getId(), uid, OperationType.SELL, TokenOrderState.PAID,
				TokenOrderState.SHIPPED);

		String buyMessage = environmentContxt.getMsg(PushTradeMessageConst.OFFLINE_SELL_SHIPP,
				DateUtils.formatDate(tokenOrder.getCreateDate()), tokenOrder.getOrderNo(),
				tokenOrder.getNum().stripTrailingZeros().toPlainString());

		smsMessageService.sendPhoneMessage(userFacade.getUser(tokenOrder.getBuyUid()).getMobile(), buyMessage);
	}

}
