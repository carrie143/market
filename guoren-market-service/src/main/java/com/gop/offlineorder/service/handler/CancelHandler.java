package com.gop.offlineorder.service.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gop.code.consts.OfflineOrderConst;
import com.gop.domain.TokenOrder;
import com.gop.domain.enums.OperationType;
import com.gop.domain.enums.TokenOrderState;
import com.gop.exception.AppException;
import com.gop.offlineorder.service.TokenOrderService;
import com.gop.offlineorder.service.TokenOrderStateHandler;

@Service("cancelHandler")
public class CancelHandler implements TokenOrderStateHandler {
	@Autowired
	private TokenOrderService tokenOrderService;
	

	@Override
	public TokenOrderState getHandlerState() {

		return TokenOrderState.CANCEL;
	}

	@Override
	@Transactional
	public void HandlerTokenOrderState(int uid, TokenOrderState changeState, TokenOrder tokenOrder) {
		if (!tokenOrder.getState().equals(TokenOrderState.WAITING)) {
			throw new AppException(OfflineOrderConst.TOKEN_ORDER_STATE_ERROR);
		}

		OperationType operationType = null;
		if (uid == tokenOrder.getBuyUid().intValue()) {
			operationType = OperationType.BUY;
		} else if (uid == tokenOrder.getSellUid().intValue()) {
			operationType = OperationType.SELL;
		} else {
			operationType = OperationType.OTHER;
		}
		tokenOrderService.updateTokenOrderStatus(tokenOrder.getId(), uid, operationType, TokenOrderState.WAITING,
				TokenOrderState.CANCEL);
	}

}
