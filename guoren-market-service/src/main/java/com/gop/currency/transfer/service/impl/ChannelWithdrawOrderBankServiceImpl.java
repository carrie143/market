//package com.gop.currency.transfer.service.impl;
//
//import java.math.BigDecimal;
//import java.math.RoundingMode;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.dao.DuplicateKeyException;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.gop.asset.dto.AssetOperationDto;
//import com.gop.code.consts.CommonCodeConst;
//import com.gop.code.consts.WithdrawalsCodeConst;
//import com.gop.coin.transfer.service.ConfigAssetProfileService;
//import com.gop.currency.transfer.service.ChannelCurrencyWithdrawOrderService;
//import com.gop.currency.transfer.service.WithdrawCurrencyService;
//import com.gop.domain.ChannelBankUserAccount;
//import com.gop.domain.WithdrawCurrencyOrderUser;
//import com.gop.domain.enums.WithdrawCurrencyOrderStatus;
//import com.gop.exception.AppException;
//import com.gop.financecheck.enums.AccountClass;
//import com.gop.financecheck.enums.AccountSubject;
//import com.gop.financecheck.enums.BusinessSubject;
//import com.gop.mapper.ChannelBankUserAccountMapper;
//import com.gop.mapper.WithdrawCurrencyOrderUserMapper;
//import com.gop.user.dto.UserSimpleInfoDto;
//import com.gop.user.facade.UserFacade;
//import com.gop.util.BigDecimalUtils;
//import com.gop.util.OrderUtil;
//
//import lombok.extern.slf4j.Slf4j;
//
//@Service("withdrawOrderBankServiceImpl")
//@Slf4j
//public class ChannelWithdrawOrderBankServiceImpl implements ChannelCurrencyWithdrawOrderService {
//
//	@Autowired
//	private WithdrawCurrencyOrderUserMapper withdrawOrderMapper;
//
//	@Autowired
//	private UserFacade userFace;
//
//	@Autowired
//	private ChannelBankUserAccountMapper channelBankUserAccountMapper;
//
//	@Autowired
//	private ConfigAssetProfileService configAssetProfileService;
//	@Autowired
//	private WithdrawCurrencyService withdrawCurrencyService;
//
//	@Override
//	@Transactional
//	public void withdrawOrder(Integer channelWithdrawId, String externalOrderNo, String assetCode, Integer uid,
//			BigDecimal money, String memo) {
//
//		checkMoney(assetCode, money);
//
//		ChannelBankUserAccount account = channelBankUserAccountMapper.selectByPrimaryKey(channelWithdrawId);
//
//		WithdrawCurrencyOrderUser userOrder = new WithdrawCurrencyOrderUser();
//		UserSimpleInfoDto user = userFace.getUserInfoByUid(uid);
//		String txid = OrderUtil.generateCode(OrderUtil.TRANSFER_SERVICE, OrderUtil.TRANSFER_OUT_CURRENCY);
//
//		userOrder.setAccount(user.getUserAccount());
//		userOrder.setBrokerId(user.getBrokerId());
//		userOrder.setAcnumber(account.getAcnumber());
//		userOrder.setAssetCode(assetCode);
//		userOrder.setBank(account.getBank());
//		userOrder.setChannelWithdrawId(account.getId());
//		userOrder.setMsg(memo);
//		userOrder.setName(account.getName());
//		userOrder.setStatus(WithdrawCurrencyOrderStatus.WAIT);
//		userOrder.setInnerOrderNo(txid);
//		userOrder.setOuterOrderNo(externalOrderNo);
//		userOrder.setUid(uid);
//
//		BigDecimal fee = BigDecimal.ZERO;
//
//		BigDecimal pay = money.subtract(fee);
//
//		userOrder.setMoney(money);
//		userOrder.setPay(pay);
//		userOrder.setFee(fee);
//
//		userOrder.setCreateDate(new Date());
//		userOrder.setUpdateDate(new Date());
//		try {
//			if (withdrawOrderMapper.insertSelective(userOrder) < 1) {
//				log.info("用户转出订单添加失败,uid:{},assetCode{},money{}", uid, assetCode, money);
//				throw new AppException(CommonCodeConst.SERVICE_ERROR, "订单添加失败");
//			}
//		} catch (DuplicateKeyException e) {
//			throw new AppException(WithdrawalsCodeConst.WITHDRAWAL_CURRENCY_RECORD_HAS_EXIST, "重复订单号");
//		}
//		// 完成扣款操作
//		deductMoney(userOrder);
//	}
//
//	/**
//	 * 券商挂单接口 因为券商挂单手续费从外部扣除，有些许不一致
//	 */
//	@Override
//	@Transactional
//	public void withdrawOrder(int uid, String assetCode, String externalOrderNo, String accountName, BigDecimal money,
//			BigDecimal fee, String bankName, String bankNo) {
//
//		// 商户用户检查实际扣款金额
//		checkMoney(assetCode, money.subtract(fee));
//
//		WithdrawCurrencyOrderUser userOrder = new WithdrawCurrencyOrderUser();
//		UserSimpleInfoDto user = userFace.getUserInfoByUid(uid);
//		String txid = OrderUtil.generateCode(OrderUtil.TRANSFER_SERVICE, OrderUtil.TRANSFER_OUT_CURRENCY);
//
//		userOrder.setAccount(user.getUserAccount());
//		userOrder.setBrokerId(user.getBrokerId());
//		userOrder.setAcnumber(bankNo);
//		userOrder.setAssetCode(assetCode);
//		userOrder.setBank(bankName);
//		userOrder.setChannelWithdrawId(0);
//		userOrder.setMsg("");
//		userOrder.setName(accountName);
//		userOrder.setStatus(WithdrawCurrencyOrderStatus.WAIT);
//		userOrder.setInnerOrderNo(txid);
//		userOrder.setOuterOrderNo(externalOrderNo);
//		userOrder.setUid(uid);
//
//		BigDecimal pay = money.subtract(fee);
//
//		userOrder.setMoney(money);
//		userOrder.setPay(pay);
//		userOrder.setFee(fee);
//
//		userOrder.setCreateDate(new Date());
//		userOrder.setUpdateDate(new Date());
//
//		try {
//			if (withdrawOrderMapper.insertSelective(userOrder) < 1) {
//				log.info("用户转出订单添加失败,uid:{},assetCode{},money{}", uid, assetCode, money);
//				throw new AppException(CommonCodeConst.SERVICE_ERROR, "订单添加失败");
//			}
//		} catch (DuplicateKeyException e) {
//			throw new AppException(WithdrawalsCodeConst.WITHDRAWAL_CURRENCY_RECORD_HAS_EXIST, "重复订单号");
//		}
//
//		deductMoney(userOrder);
//
//	}
//
//	private void checkMoney(String assetCode, BigDecimal money) {
//
//		BigDecimal min = null;
//		// configAssetProfileService.getBigDecimalValue(assetCode,
//		// "withdrawmin");
//		BigDecimal max = null;
//		// configAssetProfileService.getBigDecimalValue(assetCode,
//		// "withdrawmax");
//		if (BigDecimalUtils.isLess(money, min)) {
//			log.info("转出订单金额不足.最小金额:{},实际金额{}", min, money);
//			throw new AppException(WithdrawalsCodeConst.LESS_MIN_WITHDRAWAL_CURRENCY_AMOUNT, "",
//					min.stripTrailingZeros().toPlainString());
//		}
//
//		if (BigDecimalUtils.isBigger(money, max)) {
//			log.info("转出订单金额过高.最大金额:{},实际金额{}", max, money);
//			throw new AppException(WithdrawalsCodeConst.SUPER_MAX_WITHDRAWAL_CURRENCY_AMOUNT, "",
//					max.stripTrailingZeros().toPlainString());
//		}
//	}
//
//	private void deductMoney(WithdrawCurrencyOrderUser order) {
//		List<AssetOperationDto> ops = new ArrayList<>();
//		AssetOperationDto withdrawDto = new AssetOperationDto();
//		withdrawDto.setAccountClass(AccountClass.LIABILITY);
//		withdrawDto.setAccountSubject(AccountSubject.WITHDRAW_COMMON);
//		withdrawDto.setAmount(BigDecimal.ZERO.subtract(order.getPay()));
//		withdrawDto.setAssetCode(order.getAssetCode());
//		withdrawDto.setBusinessSubject(BusinessSubject.WITHDRAW);
//		withdrawDto.setLoanAmount(BigDecimal.ZERO);
//		withdrawDto.setLockAmount(BigDecimal.ZERO);
//		withdrawDto.setMemo(order.getAssetCode() + "提现");
//		withdrawDto.setRequestNo(order.getInnerOrderNo());
//		withdrawDto.setUid(order.getUid());
//
//		if (!order.getFee().equals(BigDecimal.ZERO)) {
//			AssetOperationDto feeDto = new AssetOperationDto();
//			feeDto.setAccountClass(AccountClass.LIABILITY);
//			feeDto.setAccountSubject(AccountSubject.FEE_WITHDRAW_SPEND);
//			feeDto.setAmount(BigDecimal.ZERO.subtract(order.getFee()));
//			feeDto.setAssetCode(order.getAssetCode());
//			feeDto.setBusinessSubject(BusinessSubject.FEE);
//			feeDto.setLoanAmount(BigDecimal.ZERO);
//			feeDto.setLockAmount(BigDecimal.ZERO);
//			feeDto.setMemo(order.getAssetCode() + "提现手续费");
//			feeDto.setRequestNo(order.getInnerOrderNo());
//			feeDto.setUid(order.getUid());
//			ops.add(feeDto);
//		}
//
//		ops.add(withdrawDto);
//
//	}
//
//	@Override
//	public BigDecimal getWithdrawFee(String assetCode, BigDecimal money) {
//		// BigDecimal feeratio =
//		// configAssetProfileService.getBigDecimalValue(assetCode,
//		// "withdrawfeeratio");
//		// Integer feeprecision =
//		// configAssetProfileService.getIntegerValue(assetCode,
//		// "withdrawfeeprecision");
//		// BigDecimal minfee =
//		// configAssetProfileService.getBigDecimalValue(assetCode,
//		// "withdrawminfee");
//
//		// BigDecimal fee = money.multiply(feeratio).setScale(feeprecision,
//		// RoundingMode.FLOOR);
//
//		// return fee.compareTo(minfee) > 0 ? fee : minfee;
//		return null;
//	}
//
//}
