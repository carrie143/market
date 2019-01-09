//package com.gop.currency.transfer.service.impl;
//
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.github.pagehelper.PageHelper;
//import com.github.pagehelper.PageInfo;
//import com.gop.asset.dto.AssetOperationDto;
//import com.gop.code.consts.CommonCodeConst;
//import com.gop.code.consts.MessageConst;
//import com.gop.code.consts.OrderCodeConst;
//import com.gop.code.consts.WithdrawalsCodeConst;
//import com.gop.common.Environment;
//import com.gop.common.SendMessageService;
//import com.gop.common.SmsMessageService;
//import com.gop.config.SmsMessageConfig;
//import com.gop.currency.transfer.dto.WithdrawCurrencyDetailDto;
//import com.gop.currency.transfer.service.WithdrawCurrencyService;
//import com.gop.currency.withdraw.gateway.facade.PayFacadeService;
//import com.gop.domain.WithdrawCurrencyOrderUser;
//import com.gop.domain.enums.WithdrawCurrencyOrderStatus;
//import com.gop.domain.enums.WithdrawCurrencyPayMode;
//import com.gop.exception.AppException;
//import com.gop.financecheck.enums.AccountClass;
//import com.gop.financecheck.enums.AccountSubject;
//import com.gop.financecheck.enums.BusinessSubject;
//import com.gop.mapper.WithdrawCurrencyOrderUserMapper;
//import com.gop.mode.vo.ProduceLogVo;
//import com.gop.notify.dto.NotifyDto;
//import com.gop.notify.dto.enums.NotifyType;
//import com.gop.user.dto.UserSimpleInfoDto;
//import com.gop.user.facade.UserFacade;
//import com.gop.user.service.UserMessageService;
//import com.gop.util.BigDecimalUtils;
//import com.gop.util.DateUtils;
//
//import lombok.extern.slf4j.Slf4j;
//
//@Service
//@Slf4j
//public class WithdrawCurrencyServiceImpl implements WithdrawCurrencyService {
//
//	@Autowired
//	Environment environmentContxt;
//
//	@Autowired
//	private UserMessageService userMessageService;
//
//	@Autowired
//	private SmsMessageService smsMessageService;
//
//	@Autowired
//	private UserFacade userFace;
//
//	@Autowired
//	private PayFacadeService payFacadeService;
//
//	@Autowired
//	private WithdrawCurrencyOrderUserMapper withdrawOrderUserMapper;
//
//	// @Autowired
//	// private WithdrawCurrencyOrderOfflineMapper withdrawOrderOfflineMapper;
//	@Autowired
//	private SendMessageService sendMessageService;
//
//	@Value("${exchange}")
//	private String exchange;
//	@Value("${callBackBusiness.key}")
//	private String callBackBusinessKey;
//
//	@Override
//	@Transactional
//	public void withdrawByChannel(int id, String payMode, int adminId) {
//
//		WithdrawCurrencyOrderUser order = withdrawOrderUserMapper.selectForUpdate(id);
//		if (null == order) {
//			throw new AppException(WithdrawalsCodeConst.WITHDRAWAL_COIN_RECORD_NOT_EXIST, null);
//		}
//		if (order.getStatus().equals(WithdrawCurrencyOrderStatus.PROCESSING)
//				|| order.getStatus().equals(WithdrawCurrencyOrderStatus.SUCCESS)) {
//			log.info("支付业务订单重复提交，订单号为:{}", order.getInnerOrderNo());
//			throw new AppException(WithdrawalsCodeConst.WITHDRAWAL_CURRENCY_STATE_ERROR, "订单状态错误");
//		}
//
//		WithdrawCurrencyPayMode pMode = null;
//		try {
//			pMode = WithdrawCurrencyPayMode.valueOf(payMode);
//		} catch (Exception e) {
//			throw new AppException(CommonCodeConst.SERVICE_ERROR, "支付模式参数错误");
//		}
//		try {
//			payFacadeService.pay(pMode, order.getInnerOrderNo(), order.getAcnumber(), order.getName(),
//					order.getAssetCode(), order.getPay(), null);
//		} catch (AppException e) {
//			log.info("支付业务失败，订单号为:{},错误原因:{}", order.getInnerOrderNo(), e.getMessage());
//			throw e;
//		} catch (Exception e) {
//			log.info("未知错误类型，订单号为:{},错误原因:{}", order.getInnerOrderNo(), e.getMessage());
//			throw e;
//		}
//		order.setStatus(WithdrawCurrencyOrderStatus.PROCESSING);
//
//		order.setUpdateDate(new Date());
//		order.setPayMode(pMode);
//		order.setAdminId(adminId);
//		withdrawOrderUserMapper.updateByPrimaryKeySelective(order);
//	}
//
//	@Override
//	@Transactional
//	public void withdrawByOffline(int id, int adminId) {
//
//		WithdrawCurrencyOrderUser order = withdrawOrderUserMapper.selectForUpdate(id);
//		if (order.getStatus().equals(WithdrawCurrencyOrderStatus.PROCESSING)
//				|| order.getStatus().equals(WithdrawCurrencyOrderStatus.SUCCESS)) {
//			log.info("支付业务订单重复提交，订单号为:{}", order.getInnerOrderNo());
//			throw new AppException(OrderCodeConst.ORDER_STATUS_ERROR, "订单状态错误", order.getStatus());
//		}
//		order.setStatus(WithdrawCurrencyOrderStatus.PROCESSING);
//		order.setUpdateDate(new Date());
//		order.setPayMode(WithdrawCurrencyPayMode.OFFLINE);
//		order.setAdminId(adminId);
//		withdrawOrderUserMapper.updateByPrimaryKeySelective(order);
//		sendCallBackMessage(order);
//	}
//
//	@Override
//	@Transactional
//	public void cancel(int id, int adminId) {
//
//		int num = 0;
//		WithdrawCurrencyOrderUser order = withdrawOrderUserMapper.selectForUpdate(id);
//		if (order.getStatus().equals(WithdrawCurrencyOrderStatus.WAIT)) {
//			order.setStatus(WithdrawCurrencyOrderStatus.CANCEL);
//			order.setAdminId(adminId);
//			order.setUpdateDate(new Date());
//			// transferCny.setUpdateip(ip);
//			num = withdrawOrderUserMapper.updateByPrimaryKey(order);
//			if (num <= 0) {
//				log.error("取消人民币转出：更新转帐表时出错，转帐id{}", id);
//				throw new AppException(CommonCodeConst.SERVICE_ERROR, "取消人民币转出：更新转帐表时出错");
//			}
//
//			BigDecimal pay = order.getPay();
//			BigDecimal fee = order.getFee();
//			String txid = order.getInnerOrderNo();
//			Integer uid = order.getUid();
//
//			// 操作时间
//			// Integer createTimeNow = SystemUtils.getSystemTimeNowSecond();
//
//			List<AssetOperationDto> ops = new ArrayList<>();
//
//			AssetOperationDto withdrawDto = new AssetOperationDto();
//			withdrawDto.setAccountClass(AccountClass.LIABILITY);
//			withdrawDto.setAccountSubject(AccountSubject.WITHDRAW_RETURN);
//			withdrawDto.setAssetCode(order.getAssetCode());
//			withdrawDto.setBusinessSubject(BusinessSubject.WITHDRAW_RETURN);
//			withdrawDto.setAmount(BigDecimal.ZERO.subtract(pay));
//			withdrawDto.setLoanAmount(BigDecimal.ZERO);
//			withdrawDto.setLockAmount(BigDecimal.ZERO);
//			withdrawDto.setMemo(order.getAssetCode() + "提现退款");
//			withdrawDto.setRequestNo(txid);
//			withdrawDto.setUid(uid);
//			withdrawDto.setIndex(0);
//			ops.add(withdrawDto);
//			if (BigDecimalUtils.isBiggerZero(fee)) {
//				AssetOperationDto feeDto = new AssetOperationDto();
//				feeDto.setAccountClass(AccountClass.LIABILITY);
//				feeDto.setAccountSubject(AccountSubject.FEE_WITHDRAW_RETURN);
//				feeDto.setAmount(BigDecimal.ZERO.subtract(fee));
//				feeDto.setAssetCode(order.getAssetCode());
//				feeDto.setBusinessSubject(BusinessSubject.FEE_RETURN);
//				feeDto.setLoanAmount(BigDecimal.ZERO);
//				feeDto.setLockAmount(BigDecimal.ZERO);
//				feeDto.setMemo(order.getAssetCode() + "提现手续费退款");
//				feeDto.setRequestNo(txid);
//				feeDto.setUid(uid);
//				feeDto.setIndex(1);
//				ops.add(feeDto);
//			}
//
//			try {
//				String smsMessage = generateTransferOutMessage(order);
//
//				int bool = userMessageService.insertMessage(order.getUid(), smsMessage);
//				if (bool > 0) {
//					UserSimpleInfoDto user = userFace.getUserInfoByUid(order.getUid());
//					smsMessageService.sendEmailMessage(user.getUserAccount(), smsMessage);
//				}
//			} catch (Exception e) {
//				log.error("退款后发送消息异常,id={}", id, e);
//			}
//		}
//		sendCallBackMessage(order);
//
//	}
//
//	@Override
//	@Transactional
//	public void refund(int id, int adminId) {
//		int num = 0;
//
//		WithdrawCurrencyOrderUser order = withdrawOrderUserMapper.selectForUpdate(id);
//		if (!order.getStatus().equals(WithdrawCurrencyOrderStatus.WAIT)
//				&& !order.getStatus().equals(WithdrawCurrencyOrderStatus.UNKNOWN)) {
//			log.error("拒绝提现状态异常");
//			throw new AppException(OrderCodeConst.ORDER_STATUS_ERROR, "订单状态异常", order.getStatus());
//		}
//
//		order.setStatus(WithdrawCurrencyOrderStatus.FAILURE);
//		// 更新订单表中失败信息
//		//
//		// if (order.getPayMode().equals(WithdrawCurrencyPayMode.OFFLINE)) {
//		// order.setMsg("风控拒绝");
//		// }
//		// /*
//		// * 超级代付消息
//		// */
//		// if (order.getPayMode().equals(WithdrawCurrencyPayMode.SUPERPAY)) {
//		// // transferCny.setMsg("风控拒绝");
//		// }
//
//		order.setAdminId(adminId);
//		order.setUpdateDate(new Date());
//		// if (ip != null) {
//		// transferCny.setUpdateip(ip);
//		// }
//		num = withdrawOrderUserMapper.updateByPrimaryKey(order);
//		if (num <= 0) {
//			log.error("人民币转出退款：更新转帐表时出错，转帐id{}", order.getId());
//			throw new AppException(CommonCodeConst.SERVICE_ERROR, "人民币转出退款：更新转帐表时出错");
//		}
//
//		// 转出：
//		// 创建转出单时，已经将userFinance中cny_balance减去转出金额money（pay+fee）, 所以此处需加回
//		// UserFinance userFinance =
//		// userFinanceMapper.getAndLockUserFinance(transferCny.getUid());
//
//		// 注意，mybatis的缓存机制，在这里不能赋值
//		// userFinance.setCurrencyBalance(userFinance.getCurrencyBalance().add(transferCny.getMoney()));
//
//		// userFinance.setUpdateDate(new Date());
//		// userFinance.setVersion(balanceVersion2);
//		// userFinance.setUpdateDate(createDateNow);
//		//
//		// num =
//		// userFinanceMapper.UpdateUserFinanceWithVersion(userFinance);
//		// if (num <= 0) {
//		// logger.error("人民币转出退款：更新用户资产表时出错，转帐id{}", transferCny.getId());
//		// throw new AppException("人民币转出退款：更新用户资产表时出错");
//		// }
//
//		// 更新资产，并向流水消息队列发送流水消息
//		// 初始化数据
//		BigDecimal pay = order.getPay();
//		BigDecimal fee = order.getFee();
//		String txid = order.getInnerOrderNo();
//		Integer uid = order.getUid();
//
//		// 操作时间
//
//		List<AssetOperationDto> ops = new ArrayList<>();
//
//		AssetOperationDto refundDto = new AssetOperationDto();
//		refundDto.setAccountClass(AccountClass.LIABILITY);
//		refundDto.setAccountSubject(AccountSubject.WITHDRAW_RETURN);
//		refundDto.setAssetCode(order.getAssetCode());
//		refundDto.setBusinessSubject(BusinessSubject.WITHDRAW_RETURN);
//		refundDto.setAmount(pay);
//		refundDto.setLoanAmount(BigDecimal.ZERO);
//		refundDto.setLockAmount(BigDecimal.ZERO);
//		refundDto.setMemo(order.getAssetCode() + "提现退款");
//		refundDto.setRequestNo(txid);
//		refundDto.setUid(uid);
//		ops.add(refundDto);
//		if (BigDecimalUtils.isBiggerZero(fee)) {
//			AssetOperationDto feeDto = new AssetOperationDto();
//			feeDto.setAccountClass(AccountClass.LIABILITY);
//			feeDto.setAccountSubject(AccountSubject.FEE_WITHDRAW_RETURN);
//			feeDto.setAmount(fee);
//			feeDto.setAssetCode(order.getAssetCode());
//			feeDto.setBusinessSubject(BusinessSubject.FEE_RETURN);
//			feeDto.setLoanAmount(BigDecimal.ZERO);
//			feeDto.setLockAmount(BigDecimal.ZERO);
//			feeDto.setMemo(order.getAssetCode() + "提现手续费退款");
//			feeDto.setRequestNo(txid);
//			feeDto.setUid(uid);
//			ops.add(feeDto);
//		}
//
//		// 给用户发送消息失败不影响事务
//		try {
//			String message = generateTransferOutMessage(order);
//			userMessageService.insertMessage(order.getUid(), message);
//
//			UserSimpleInfoDto user = userFace.getUserInfoByUid(order.getUid());
//
//			smsMessageService.sendEmailMessage(user.getUserAccount(), message);
//		} catch (Exception e) {
//			log.info("给用户发送消息失败");
//		}
//		sendCallBackMessage(order);
//	}
//
//	@Override
//	@Transactional
//	public void lock(int id, int adminId) {
//		WithdrawCurrencyOrderUser transferCny = withdrawOrderUserMapper.selectForUpdate(id);
//		if ((transferCny.getStatus().equals(WithdrawCurrencyOrderStatus.WAIT) && transferCny.getPayMode() == null)
//				|| transferCny.getStatus().equals(WithdrawCurrencyOrderStatus.UNKNOWN)) {
//			transferCny.setStatus(WithdrawCurrencyOrderStatus.PROCESSING);
//			transferCny.setAdminId(adminId);
//			transferCny.setUpdateDate(new Date());
//			transferCny.setPayMode(WithdrawCurrencyPayMode.OFFLINE);
//			if (withdrawOrderUserMapper.updateByPrimaryKey(transferCny) < 1) {
//				throw new AppException(CommonCodeConst.SERVICE_ERROR, "锁定订单失败");
//			}
//		}
//
//	}
//
//	@Override
//	@Transactional
//	public void unlock(int id, int adminId) {
//		WithdrawCurrencyOrderUser transferCny = withdrawOrderUserMapper.selectForUpdate(id);
//		if (transferCny.getStatus().equals(WithdrawCurrencyOrderStatus.PROCESSING)) {
//			transferCny.setStatus(WithdrawCurrencyOrderStatus.WAIT);
//			transferCny.setAdminId(adminId);
//			transferCny.setUpdateDate(new Date());
//			// transferCny.setUpdateip(ip);
//			transferCny.setPayMode(null);
//			if (withdrawOrderUserMapper.updateByPrimaryKey(transferCny) < 1) {
//				throw new AppException(CommonCodeConst.SERVICE_ERROR, "解锁订单失败");
//			}
//
//		}
//
//	}
//
//	@Override
//	public PageInfo<WithdrawCurrencyOrderUser> queryOrder(Integer uid, String assetCode, Integer pageNo,
//			Integer pageSize) {
//		PageHelper.startPage(pageNo, pageSize);
//		PageHelper.orderBy("id desc");
//		return new PageInfo<>(withdrawOrderUserMapper.getUserOrdersByAsset(uid, assetCode));
//	}
//
//	@Override
//	public PageInfo<WithdrawCurrencyOrderUser> queryOrder(Integer id, Integer brokerId, Integer uId,
//			String innerOrderNo, String account, String name, WithdrawCurrencyOrderStatus status, Integer pageNo,
//			Integer pageSize) {
//		PageHelper.startPage(pageNo, pageSize);
//		PageHelper.orderBy("create_date desc");
//		return new PageInfo<>(withdrawOrderUserMapper.getWithdrawOrderList(brokerId, id, uId, innerOrderNo, name,
//				account, status, null));
//	}
//
//	@Override
//	public WithdrawCurrencyOrderUser getOrder(int id) {
//
//		WithdrawCurrencyOrderUser order = withdrawOrderUserMapper.selectForUpdate(id);
//		if (null == order) {
//			throw new AppException(WithdrawalsCodeConst.WITHDRAWAL_CURRENCY_RECORD_NOT_EXIST);
//		}
//		return order;
//	}
//
//	@Override
//	public WithdrawCurrencyOrderUser getOrder(int uid, String outerOrderNo) {
//
//		WithdrawCurrencyOrderUser order = withdrawOrderUserMapper.selectByUidAndOuterOrderNo(uid, outerOrderNo);
//		if (null == order) {
//			throw new AppException(WithdrawalsCodeConst.WITHDRAWAL_CURRENCY_RECORD_NOT_EXIST);
//		}
//		return order;
//	}
//
//	public String generateTransferOutMessage(WithdrawCurrencyOrderUser order) {
//
//		String message = null;
//
//		String bankNo = order.getAcnumber();
//		String bankName = order.getBank();
//		switch (order.getStatus()) {
//		case SUCCESS:
//			message = environmentContxt.getMsg(MessageConst.CASH_WITHDRAW_SUCCESS_MESSAGE,
//					order.getPay().setScale(2).toString(), order.getAssetCode(),
//					DateUtils.formatDate(order.getCreateDate()), bankNo.substring(bankNo.length() - 4), bankName);
//			break;
//		case FAILURE:
//			message = environmentContxt.getMsg(MessageConst.CASH_WITHDRAW_FAIL_MESSAGE,
//					order.getMoney().setScale(2).toString(), order.getAssetCode(),
//					DateUtils.formatDate(order.getCreateDate()));
//			break;
//		default:
//			break;
//		}
//		return message;
//	}
//
//	private void sendMessage(String message, Integer uid) {
//		try {
//			userMessageService.insertMessage(uid, message);
//
//			UserSimpleInfoDto user = userFace.getUserInfoByUid(uid);
//
//			smsMessageService.sendEmailMessage(user.getUserAccount(), message);
//		} catch (Exception e) {
//			log.info("给用户发送消息失败");
//		}
//	}
//
//	@Override
//	@Transactional
//	public void confirmOffline(Integer id, Integer adminId) {
//		WithdrawCurrencyOrderUser order = withdrawOrderUserMapper.selectForUpdate(id);
//		if (null == order) {
//			throw new AppException(WithdrawalsCodeConst.WITHDRAWAL_COIN_RECORD_NOT_EXIST, null);
//		}
//		if (!order.getPayMode().equals(WithdrawCurrencyPayMode.OFFLINE)) {
//			log.info("支付业务订单重复提交，订单号为:{}", order.getInnerOrderNo());
//			throw new AppException(WithdrawalsCodeConst.WITHDRAWAL_COIN_RECORD_NOT_EXIST, "订单不是线下");
//		}
//		order.setStatus(WithdrawCurrencyOrderStatus.SUCCESS);
//		withdrawOrderUserMapper.updateByPrimaryKey(order);
//
//		String message = generateTransferOutMessage(order);
//		sendMessage(message, order.getUid());
//		sendCallBackMessage(order);
//
//	}
//
//	@Override
//	public void confirm(WithdrawCurrencyOrderUser order) {
//		order.setStatus(WithdrawCurrencyOrderStatus.SUCCESS);
//		order.setUpdateDate(new Date());
//		withdrawOrderUserMapper.updateByPrimaryKey(order);
//
//		String message = generateTransferOutMessage(order);
//		sendMessage(message, order.getUid());
//		sendCallBackMessage(order);
//	}
//
//	@Override
//	public void toUnknown(WithdrawCurrencyOrderUser order) {
//		order.setStatus(WithdrawCurrencyOrderStatus.UNKNOWN);
//		order.setUpdateDate(new Date());
//		withdrawOrderUserMapper.updateByPrimaryKey(order);
//
//	}
//
//	@Override
//	public List<WithdrawCurrencyOrderUser> getTransferCnyList(WithdrawCurrencyOrderStatus status,
//			WithdrawCurrencyPayMode payMode, int limit) {
//		PageHelper.offsetPage(0, limit);
//		return withdrawOrderUserMapper.selectByStatusAndPayMode(status, payMode);
//	}
//
//	private void sendCallBackMessage(WithdrawCurrencyOrderUser order) {
//
//		NotifyDto notify = new NotifyDto();
//		notify.setNotifyType(NotifyType.TRANSFERCURRENCY_OUT);
//		notify.setCode(CommonCodeConst.SERIVCE_SUCCESS);
//		notify.setUserId(order.getUid());
//		notify.setData(new WithdrawCurrencyDetailDto(order));
//		ProduceLogVo logVo = new ProduceLogVo();
//		logVo.setExchangeName(exchange);
//		logVo.setKey(callBackBusinessKey);
//		logVo.setMessage(notify);
//		try {
//			long pid = sendMessageService.tryMessage(logVo);
//			sendMessageService.commitMessage(pid);
//		} catch (Exception e) {
//			log.error("发送消息失败{}", e);
//		}
//
//	}
//
//	// private String generateRefundMessage(WithdrawCurrencyOrderUser
//	// transferCny) {
//	// // StringBuilder sb = null;
//	// // sb = new StringBuilder("亲爱的果仁用户，您的账户于");
//	// // sb.append(DateUtils.formatDate(transferCny.getCreateDate()));
//	// // sb.append("有一笔人民币转出申请，由于您提交的信息与银行信息不符，该笔提款已被银行退回，退款已回到您的账户余额。");
//	// // sb.append("请认真核实您的信息后再次提交。如有问题请联系客服：" +
//	// // CommonConstants.CUSTOMER_HOTLINE + "。");
//	// // return sb.toString();
//	// String message =
//	// environmentContxt.getMsg(MessageConst.WITHDRAW_REFUSEM_MESSAGE,
//	// transferCny.getMoney().setScale(2).toString(),
//	// transferCny.getAssetCode());
//	// return message;
//	// }
//	//
//	// private String generateSuccMessage(WithdrawCurrencyOrderUser order) {
//	// // StringBuilder sb = null;
//	// // sb = new StringBuilder("亲爱的果仁用户，您的账户于");
//	// // sb.append(DateUtils.formatDate(transferCny.getCreateDate()));
//	// // sb.append("有一笔人民币转出申请，由于您提交的信息与银行信息不符，该笔提款已被银行退回，退款已回到您的账户余额。");
//	// // sb.append("请认真核实您的信息后再次提交。如有问题请联系客服：" +
//	// // CommonConstants.CUSTOMER_HOTLINE + "。");
//	// // return sb.toString();
//	// String message =
//	// environmentContxt.getMsg(MessageConst.WITHDRAW_SUCCESS_MESSAGE,
//	// order.getMoney().setScale(2).toString(), order.getAssetCode(),
//	// DateUtils.formatDate(new Date()));
//	// return message;
//	// }
//
//}
