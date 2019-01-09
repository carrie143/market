package com.gop.offlineorder.service;

import com.gop.domain.TokenOrder;
import com.gop.domain.enums.TokenOrderState;

public interface TokenOrderStateHandler {

	public TokenOrderState getHandlerState();

	public void HandlerTokenOrderState(int uid, TokenOrderState changeState, TokenOrder tokenOrder);

}
