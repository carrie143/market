package com.gop.currency.transfer.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gop.asset.dto.AssetOperationDto;
import com.gop.code.consts.CommonCodeConst;
import com.gop.currency.transfer.service.UnCerfWithdrawCurrencyService;
import com.gop.domain.WithdrawCurrencyOrderUser;
import com.gop.domain.enums.WithdrawCurrencyOrderStatus;
import com.gop.exception.AppException;
import com.gop.financecheck.enums.AccountClass;
import com.gop.financecheck.enums.AccountSubject;
import com.gop.financecheck.enums.BusinessSubject;
import com.gop.mapper.WithdrawCurrencyOrderUserMapper;
import com.gop.user.dto.UserSimpleInfoDto;
import com.gop.user.facade.UserFacade;
import com.gop.util.OrderUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UnCerfWithdrawCurrencyServiceImpl implements UnCerfWithdrawCurrencyService{

	@Autowired
	private WithdrawCurrencyOrderUserMapper withdrawOrderMapper;

	@Autowired
	private UserFacade userFace;

	@Override
	public void currencyWithdrawOrderUnCerf(int uid, String outOrder, String assetCode, BigDecimal amount) {

		WithdrawCurrencyOrderUser userOrder = new WithdrawCurrencyOrderUser();
		UserSimpleInfoDto user = userFace.getUserInfoByUid(uid);
		String txid = OrderUtil.generateCode(OrderUtil.TRANSFER_SERVICE, OrderUtil.TRANSFER_OUT_CURRENCY);

		userOrder.setAccount(user.getUserAccount());
		userOrder.setBrokerId(user.getBrokerId());
		userOrder.setAssetCode(assetCode);
		userOrder.setAcnumber("");
		userOrder.setBank("");
		userOrder.setName("");
		userOrder.setChannelWithdrawId(0);
		userOrder.setMsg("券商提现");
		userOrder.setStatus(WithdrawCurrencyOrderStatus.SUCCESS);
		userOrder.setInnerOrderNo(txid);
		userOrder.setOuterOrderNo(outOrder);
		userOrder.setUid(uid);

		userOrder.setMoney(amount);
		userOrder.setPay(amount);
		userOrder.setFee(BigDecimal.ZERO);

		userOrder.setCreateDate(new Date());
		userOrder.setUpdateDate(new Date());

		if (withdrawOrderMapper.insertSelective(userOrder) < 1) {
			log.info("用户转出订单添加失败,uid:{},assetCode{},money{}", uid, assetCode, amount);
			throw new AppException(CommonCodeConst.SERVICE_ERROR, "订单添加失败");
		}
		// 完成扣款操作
		deductMoney(userOrder);
	}
	private void deductMoney(WithdrawCurrencyOrderUser order) {
		List<AssetOperationDto> ops = new ArrayList<>();
		AssetOperationDto withdrawDto = new AssetOperationDto();
		withdrawDto.setAccountClass(AccountClass.LIABILITY);
		withdrawDto.setAccountSubject(AccountSubject.WITHDRAW_COMMON);
		withdrawDto.setAmount(BigDecimal.ZERO.subtract(order.getPay()));
		withdrawDto.setAssetCode(order.getAssetCode());
		withdrawDto.setBusinessSubject(BusinessSubject.WITHDRAW);
		withdrawDto.setLoanAmount(BigDecimal.ZERO);
		withdrawDto.setLockAmount(BigDecimal.ZERO);
		withdrawDto.setMemo(order.getAssetCode() + "提现");
		withdrawDto.setRequestNo(order.getInnerOrderNo());
		withdrawDto.setUid(order.getUid());

		AssetOperationDto feeDto = new AssetOperationDto();
		feeDto.setAccountClass(AccountClass.LIABILITY);
		feeDto.setAccountSubject(AccountSubject.FEE_WITHDRAW_SPEND);
		feeDto.setAmount(BigDecimal.ZERO.subtract(order.getFee()));
		feeDto.setAssetCode(order.getAssetCode());
		feeDto.setBusinessSubject(BusinessSubject.FEE);
		feeDto.setLoanAmount(BigDecimal.ZERO);
		feeDto.setLockAmount(BigDecimal.ZERO);
		feeDto.setMemo(order.getAssetCode() + "提现手续费");
		feeDto.setRequestNo(order.getInnerOrderNo());
		feeDto.setUid(order.getUid());

		ops.add(withdrawDto);
		ops.add(feeDto);
	}

}
