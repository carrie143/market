package com.gop.offlineorder.service.handler;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
import com.gop.domain.TokenOrderCompletionCoins;
import com.gop.domain.enums.OperationType;
import com.gop.domain.enums.TokenOrderState;
import com.gop.exception.AppException;
import com.gop.financecheck.enums.AccountClass;
import com.gop.financecheck.enums.AccountSubject;
import com.gop.financecheck.enums.BusinessSubject;
import com.gop.offlineorder.service.IntentionOrderSerrvice;
import com.gop.offlineorder.service.OrderInfoRankService;
import com.gop.offlineorder.service.TokenOrderService;
import com.gop.offlineorder.service.TokenOrderStateHandler;
import com.gop.user.facade.UserFacade;
import com.gop.util.DateUtils;

@Service("completeHandler")
public class CompleteHandler implements TokenOrderStateHandler {
	@Autowired
	private TokenOrderService tokenOrderService;
	@Autowired
	private IntentionOrderSerrvice intentionOrderSerrvice;
	@Autowired
	private SmsMessageService smsMessageService;
	@Autowired
	UserFacade userFacade;
	@Autowired
	Environment environmentContxt;
	@Autowired
	@Qualifier("OrderInfoRankServiceImpl")
	private OrderInfoRankService orderRankService;

	@Override
	public TokenOrderState getHandlerState() {
		// TODO Auto-generated method stub
		return TokenOrderState.COMPLETE;
	}

	@Override
	@Transactional
	public void HandlerTokenOrderState(int uid, TokenOrderState changeState, TokenOrder tokenOrder) {
		if (!tokenOrder.getState().equals(TokenOrderState.SHIPPED)) {
			throw new AppException(OfflineOrderConst.TOKEN_ORDER_STATE_ERROR);
		}

		if (uid != tokenOrder.getBuyUid().intValue()) {
			throw new AppException(OfflineOrderConst.TOKEN_ORDER_STATE_ERROR);
		}
		tokenOrder.setState(TokenOrderState.COMPLETE);

		// 给卖用户加款
		AssetOperationDto assetOperationDto = new AssetOperationDto();
		assetOperationDto.setAccountClass(AccountClass.ASSET);
		assetOperationDto.setBusinessSubject(BusinessSubject.PUSH_SELL);
		assetOperationDto.setAccountSubject(AccountSubject.UNKNOWN);
		assetOperationDto.setAmount(tokenOrder.getMoney());
		assetOperationDto.setAssetCode(tokenOrder.getTradeAsset());
		assetOperationDto.setLoanAmount(BigDecimal.ZERO);
		assetOperationDto.setLockAmount(BigDecimal.ZERO);
		assetOperationDto.setMemo("线下交易付款");
		assetOperationDto.setRequestNo(tokenOrder.getSellOrderRequestNo());
		assetOperationDto.setUid(tokenOrder.getSellUid());

		AssetOperationDto assetOperationDto2 = new AssetOperationDto();
		assetOperationDto2.setAccountClass(AccountClass.ASSET);
		assetOperationDto2.setBusinessSubject(BusinessSubject.FEE);
		assetOperationDto2.setAccountSubject(AccountSubject.FEE_MATCH_SPEND);
		assetOperationDto2.setAmount(tokenOrder.getSellFee().negate());
		assetOperationDto2.setAssetCode(tokenOrder.getTradeAsset());
		assetOperationDto2.setLoanAmount(BigDecimal.ZERO);
		assetOperationDto2.setLockAmount(BigDecimal.ZERO);
		assetOperationDto2.setMemo("线下交易付款");
		assetOperationDto2.setRequestNo(tokenOrder.getSellOrderRequestNo());
		assetOperationDto2.setUid(tokenOrder.getSellUid());
		tokenOrder.setState(TokenOrderState.PAID);

		intentionOrderSerrvice.updateUserComplete(tokenOrder.getSellUid());
		// 扣除手续费
		tokenOrderService.updateTokenOrderStatus(tokenOrder.getId(), uid, OperationType.BUY, TokenOrderState.SHIPPED,
				TokenOrderState.COMPLETE);
		// *******************统计成功交易与币种交易*************************
		// 处理卖家信息

		orderRankService.saveOrIncrOrderBuyCompleted(tokenOrder.getBuyUid());
		// 处理买家信息

		orderRankService.saveOrIncrOrderSellCompleted(tokenOrder.getSellUid());

		TokenOrderCompletionCoins coins = new TokenOrderCompletionCoins();
		coins.setAssetName(tokenOrder.getAssetName());
		coins.setCompletionNum(tokenOrder.getNum());
		orderRankService.saveOrIncrOrderCompletion(tokenOrder.getAssetName());
		// ***************************************************************

		String buyMessage = environmentContxt.getMsg(PushTradeMessageConst.OFFLINE_COMPLETE,
				DateUtils.formatDate(tokenOrder.getCreateDate()), tokenOrder.getOrderNo());

		smsMessageService.sendPhoneMessage(userFacade.getUser(tokenOrder.getSellUid()).getMobile(), buyMessage);
	}

}
