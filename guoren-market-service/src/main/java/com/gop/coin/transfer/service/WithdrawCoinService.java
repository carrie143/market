package com.gop.coin.transfer.service;

import com.gop.api.cloud.response.WithdrawCoinDetailDto;
import com.gop.exception.AppException;
import java.math.BigDecimal;
import java.util.Date;

import com.gop.api.cloud.request.WithdrawCallbackDto;
import com.gop.domain.enums.WithdrawCoinOrderStatus;

public interface WithdrawCoinService {
//
//	/**
//	 * 提交订单并直接审核 该方法只适用商户充值
//	 *
//	 * @param uid
//	 * @param outOrder
//	 * @param assetCode
//	 * @param amount
//	 * @param fee
//	 * @param address
//	 * @param message
//	 */
//	public void withdrawCoinOrderUnCerf(int uid, String outOrder, String assetCode, BigDecimal amount);

	/**
	 * 提交订单
	 *
	 * @param uid
	 * @param outOrder
	 * @param assetCode
	 * @param amount
	 * @param fee
	 * @param address
	 * @param message
	 */
	public void withdrawCoinOrder(int uid, String outOrder, String assetCode, BigDecimal amount, BigDecimal fee,
			String address, String message) throws Exception;

//	/**
//	 * 审核通过
//	 *
//	 * @param id
//	 * @param adminId
//	 */
//	public void withdraw(int id, int adminId);
//
//	/**
//	 * 退款
//	 *
//	 * @param id
//	 * @param adminId
//	 */
//	public void withdrawRefund(int id, int adminId, String refuseMs);
//
//	/**
//	 * 提现成功回调确认
//	 *
//	 * @param txid
//	 */
//	public void withdrawConfirm(String txid);
//
//	/**
//	 * 提现失败处理确认
//	 *
//	 * @param txid
//	 * @param msg
//	 *            TODO
//	 */
//	public void withdrawFail(String txid, String msg);
//
//	/**
//	 * 获取提现手续费
//	 *
//	 * @param txid
//	 * @param msg
//	 *            TODO
//	 * @return TODO
//	 */
//	public BigDecimal getWithdrawFee(BigDecimal amount);
//
	/**
	 * 获取用户每日累计已提币数量
	 *
	 */
	public BigDecimal getUserDailyWithdrawedCoinValue(Integer uid,String assetCode,Date beginDate,Date endDate);
//
//	/**
//	 * 查询对应币种指定状态的数量
//	 * @param assetCode
//	 * @param status
//	 * @return
//	 */
//	public Integer getAmountOfAssetWithStatus(String assetCode, WithdrawCoinOrderStatus status);

	//
  //  @Override
  //  @Transactional
  //  public void withdraw(int id, int adminId) {
  //
  //    WithdrawCoinOrderUser order = withdrawCoinOrderUserMapper.selectForUpdate(id);
  //
  //    if (null == order) {
  //      log.info("无效转出订单，id:{}", id);
  //      throw new AppException(CommonCodeConst.SERVICE_ERROR);
  //    }
  //
  //    String key = String.format("gdae2.transferOut.%s.key", order.getAssetCode().toLowerCase());
  //
  //    TransferCoinMessage transferCoinMessage = new TransferCoinMessage();
  //    transferCoinMessage.setTxid(order.getInnerOrderNo());
  //    transferCoinMessage.setAddress(order.getCoinAddress());
  //    transferCoinMessage.setAmount(order.getRealNumber().toString());
  //    transferCoinMessage.setTxfee(order.getTxFee().toString());
  //    transferCoinMessage.setMessage(order.getMsg());
  //
  //    ProduceLogVo produceLogVo = new ProduceLogVo();
  //    produceLogVo.setExchangeName(exchange);
  //    produceLogVo.setKey(key);
  //    produceLogVo.setMessage(transferCoinMessage);
  //    Long messageId = sendMessageService.tryMessage(produceLogVo);
  //
  //    WithdrawCoinOrderStatus status = order.getStatus();
  //    if (!status.equals(WithdrawCoinOrderStatus.WAIT) && !status
  //        .equals(WithdrawCoinOrderStatus.UNKNOWN)) {
  //      log.info("订单状态异常，id:{}", id);
  //      throw new AppException(CommonCodeConst.SERVICE_ERROR, "订单状态异常");
  //    }
  //    order.setStatus(WithdrawCoinOrderStatus.PROCESSING);
  //    order.setUpdateDate(new Date());
  //
  //    try {
  //      withdrawCoinOrderUserMapper.updateByPrimaryKeySelective(order);
  //    } catch (Exception e) {
  //      sendMessageService.rollBackMessage(messageId);
  //    }
  //
  //    try {
  //      sendMessageService.commitMessage(messageId);
  //    } catch (Exception e1) {
  //      log.error("order error" + e1);
  //      Throwables.propagate(new AppException(CommonCodeConst.SERVICE_ERROR, "发送消息到mq失败"));
  //    }
  //
  //    log.info("普通用户果仁转出: 发送消息到路由MQ,transferCoinMessage={}", transferCoinMessage);
  //
  //  }
  //
  //  @Override
  //  @Transactional
  //  public void withdrawConfirm(String txid) {
  //    WithdrawCoinOrderUser order = withdrawCoinOrderUserMapper.selectByInnerOrder(txid);
  //    if (order == null) {
  //      log.info("订单{}不存在", txid);
  //      throw new AppException(CommonCodeConst.SERVICE_ERROR);
  //    }
  //
  //    WithdrawCoinOrderStatus status = order.getStatus();
  //
  //    if (!status.equals(WithdrawCoinOrderStatus.PROCESSING)) {
  //      if (status.equals(WithdrawCoinOrderStatus.SUCCESS)) {
  //        log.info("订单已经被处理，消息重复.txid:{}", txid);
  //        return;
  //      } else {
  //        log.info("订单状态异常，txid:{}", txid);
  //        throw new AppException(CommonCodeConst.SERVICE_ERROR, "订单状态异常");
  //      }
  //    }
  //
  //    order = withdrawCoinOrderUserMapper.selectForUpdate(order.getId());
  //    if (order.getStatus() != WithdrawCoinOrderStatus.PROCESSING) {
  //      log.info("订单{}状态异常", txid);
  //      throw new AppException(CommonCodeConst.SERVICE_ERROR);
  //    }
  //    order.setStatus(WithdrawCoinOrderStatus.SUCCESS);
  //    order.setUpdateDate(new Date());
  //
  //    if (withdrawCoinOrderUserMapper.updateByPrimaryKeySelective(order) < 1) {
  //      log.info("更新订单{}失败", txid);
  //      throw new AppException(CommonCodeConst.SERVICE_ERROR);
  //    }
  //
  //    sendMessage(order);// 发送通知短信或邮件
  //    // 回调
  //
  //  }
  //
  //  @Override
  //  @Transactional
  //  public void withdrawRefund(int id, int adminId, String refuseMs) {
  //    Date currentDate = new Date();
  //
  //    int result = 0;
  //
  //    WithdrawCoinOrderUser order = withdrawCoinOrderUserMapper.selectForUpdate(id);
  //
  //    if (!order.getStatus().equals(WithdrawCoinOrderStatus.WAIT)
  //        && !order.getStatus().equals(WithdrawCoinOrderStatus.UNKNOWN)) {
  //      throw new AppException(CommonCodeConst.SERVICE_ERROR, "订单状态异常");
  //    }
  //    order.setStatus(WithdrawCoinOrderStatus.REFUSE);
  //    order.setMsg(refuseMs);
  //    order.setUpdateDate(currentDate);
  //    result = withdrawCoinOrderUserMapper.updateByPrimaryKeySelective(order);
  //    if (result <= 0) {
  //      log.info("提现-审核不通过:更新转帐记录时失败,转帐id:{}", order.getId());
  //      throw new AppException("更新转帐记录时失败");
  //    }
  //
  ////		returnMoney(order);  不冻结不返还
  //
  //    sendMessage(order);// 发送通知短信或邮件
  //  }
  //
  //  @Override
  //  @Transactional
  //  public void withdrawFail(String txid, String msg) {
  //    WithdrawCoinOrderUser order = withdrawCoinOrderUserMapper.selectByInnerOrder(txid);
  //    if (order == null) {
  //      log.info("订单{}不存在", txid);
  //      throw new AppException(CommonCodeConst.SERVICE_ERROR);
  //    }
  //
  //    order = withdrawCoinOrderUserMapper.selectForUpdate(order.getId());
  //    if (order.getStatus() != WithdrawCoinOrderStatus.PROCESSING) {
  //      log.error("订单{}状态异常", txid);
  //      return;
  //    }
  //    order.setStatus(WithdrawCoinOrderStatus.UNKNOWN);
  //    order.setUpdateDate(new Date());
  //    order.setMsg(msg);
  //    if (withdrawCoinOrderUserMapper.updateByPrimaryKeySelective(order) < 1) {
  //      log.info("更新订单{}失败", txid);
  //      throw new AppException(CommonCodeConst.SERVICE_ERROR);
  //    }
  //
  //  }
  //
  //  public DestAddressType getAddressType(String assetCode, InnerAddressFlag addressFlag) {
  //    if (addressFlag.equals(InnerAddressFlag.YES)) {
  //      return DestAddressType.INNER_ADDRESS;
  //    }
  //    return DestAddressType.OUTER_ADDRESS;
  //  }
  //
  //  public void sendMessage(WithdrawCoinOrderUser transferOut) {
  //    String transferMessage = null;
  //
  //    try {
  //      transferMessage = generateTransferMessage(transferOut);
  //      userMessageService.insertMessage(transferOut.getUid(), transferMessage);
  //
  //      UserSimpleInfoDto user = userFacade.getUserInfoByUid(transferOut.getUid());
  //      log.info("给用户{}发送邮件，信息:{}", user.getUserAccount(), transferMessage);
  //      smsMessageService.sendEmailMessage(user.getUserAccount(), transferMessage);
  //
  //    } catch (Exception e) {
  //      log.error("果仁转入正常，短信及站内信异常, 用户id={}, 短信内容={}", transferOut.getUid(), transferMessage, e);
  //    }
  //
  //  }
  //
  //  public String generateTransferMessage(WithdrawCoinOrderUser order) {
  //
  //    String message = null;
  //
  //    switch (order.getStatus()) {
  //      case SUCCESS:
  //        message = environmentContxt.getMsg(MessageConst.COIN_WITHDRAW_SUCCESS_MESSAGE,
  //            order.getNumber().setScale(4, RoundingMode.FLOOR).toPlainString(), order.getAssetCode(),
  //            DateUtils.formatDate(order.getCreateDate()));
  //        break;
  //      case FAILURE:
  //        message = environmentContxt.getMsg(MessageConst.COIN_WITHDRAW_FAIL_MESSAGE,
  //            order.getNumber().setScale(4, RoundingMode.FLOOR).toPlainString(), order.getAssetCode(),
  //            DateUtils.formatDate(order.getCreateDate()));
  //        break;
  //      case REFUSE:
  //        message = environmentContxt.getMsg(MessageConst.COIN_WITHDRAW_REFUSEM_MESSAGE,
  //            order.getNumber().setScale(4, RoundingMode.FLOOR).toPlainString(), order.getAssetCode(),
  //            DateUtils.formatDate(order.getCreateDate()));
  //        break;
  //      default:
  //        break;
  //    }
  //
  //    return message;
  //  }
  //
  //  private void deductMoney(WithdrawCoinOrderUser order) {
  //    List<AssetOperationDto> ops = new ArrayList<>();
  //    AssetOperationDto withdrawDto = new AssetOperationDto();
  //    withdrawDto.setAccountClass(AccountClass.LIABILITY);
  //    withdrawDto.setAccountSubject(AccountSubject.WITHDRAW_COMMON);
  //    withdrawDto.setAmount(BigDecimal.ZERO.subtract(order.getRealNumber()));
  //    withdrawDto.setAssetCode(order.getAssetCode());
  //    withdrawDto.setBusinessSubject(BusinessSubject.WITHDRAW);
  //    withdrawDto.setLoanAmount(BigDecimal.ZERO);
  //    withdrawDto.setLockAmount(BigDecimal.ZERO);
  //    withdrawDto.setMemo(order.getAssetCode() + "提现");
  //    withdrawDto.setRequestNo(order.getInnerOrderNo());
  //    withdrawDto.setUid(order.getUid());
  //
  //    AssetOperationDto feeDto = new AssetOperationDto();
  //    feeDto.setAccountClass(AccountClass.LIABILITY);
  //    feeDto.setAccountSubject(AccountSubject.FEE_WITHDRAW_SPEND);
  //    feeDto.setAmount(BigDecimal.ZERO.subtract(order.getTxFee()));
  //    feeDto.setAssetCode(order.getAssetCode());
  //    feeDto.setBusinessSubject(BusinessSubject.FEE);
  //    feeDto.setLoanAmount(BigDecimal.ZERO);
  //    feeDto.setLockAmount(BigDecimal.ZERO);
  //    feeDto.setMemo(order.getAssetCode() + "提现手续费");
  //    feeDto.setRequestNo(order.getInnerOrderNo());
  //    feeDto.setUid(order.getUid());
  //
  //    ops.add(withdrawDto);
  //    ops.add(feeDto);
  //  }
  //
  //  /**
  //   * 给用户退款
  //   */
  //  private void returnMoney(WithdrawCoinOrderUser order) {
  //
  //    List<AssetOperationDto> ops = new ArrayList<>();
  //
  //    AssetOperationDto withdrawDto = new AssetOperationDto();
  //    withdrawDto.setAccountClass(AccountClass.LIABILITY);
  //    withdrawDto.setAccountSubject(AccountSubject.WITHDRAW_RETURN);
  //    withdrawDto.setAssetCode(order.getAssetCode());
  //    withdrawDto.setBusinessSubject(BusinessSubject.WITHDRAW_RETURN);
  //    withdrawDto.setAmount(order.getRealNumber());
  //    withdrawDto.setLoanAmount(BigDecimal.ZERO);
  //    withdrawDto.setLockAmount(BigDecimal.ZERO);
  //    withdrawDto.setMemo(order.getAssetCode() + "提现费用退回");
  //    withdrawDto.setRequestNo(order.getInnerOrderNo());
  //    withdrawDto.setUid(order.getUid());
  //    ops.add(withdrawDto);
  //    if (BigDecimalUtils.isBiggerZero(order.getTxFee())) {
  //      AssetOperationDto feeDto = new AssetOperationDto();
  //      feeDto.setAccountClass(AccountClass.LIABILITY);
  //      feeDto.setAccountSubject(AccountSubject.FEE_WITHDRAW_RETURN);
  //      feeDto.setAmount(order.getTxFee());
  //      feeDto.setAssetCode(order.getAssetCode());
  //      feeDto.setBusinessSubject(BusinessSubject.FEE_RETURN);
  //      feeDto.setLoanAmount(BigDecimal.ZERO);
  //      feeDto.setLockAmount(BigDecimal.ZERO);
  //      feeDto.setMemo(order.getAssetCode() + "提现手续费退回");
  //      feeDto.setRequestNo(order.getInnerOrderNo());
  //      feeDto.setUid(order.getUid());
  //      ops.add(feeDto);
  //    }
  //  }
  //
  //  @Override
  //  public BigDecimal getWithdrawFee(BigDecimal amount) {
  //    throw new AppException(CommonCodeConst.SERVICE_ERROR, "btc的提现手续费由用户指定");
  //  }
  //
  //  @Transactional
  //  public void withdrawCoinOrderUnCerf(int uid, String outOrder, String assetCode,
  //      BigDecimal amount) {
  //    BigDecimal gopWithdrawFeeAmount = configAssetProfileService.getBigDecimalValue(assetCode,
  //        ConfigAssetType.WITHDRAWMINFEE);
  //
  //
  //    BigDecimal realNumber = amount.subtract(gopWithdrawFeeAmount);
  //    String txId = OrderUtil.generateCode(OrderUtil.TRANSFER_SERVICE, OrderUtil.TRANSFER_OUT_COIN);
  //
  //    UserSimpleInfoDto user = userFacade.getUserInfoByUid(uid);
  //    WithdrawCoinOrderUser order = new WithdrawCoinOrderUser();
  //    order.setBrokerId(user.getBrokerId());
  //    order.setAccount(user.getUserAccount());
  //    order.setAssetCode(assetCode);
  //    order.setChannelWithdrawId(0);
  //    order.setCoinAddress("");
  //    order.setCreateDate(new Date());
  //    order.setDestAddressType(DestAddressType.OUTER_ADDRESS);
  //    order.setInnerOrderNo(txId);
  //    order.setOuterOrderNo(outOrder);
  //    order.setMsg("券商用户" + uid + "提现");
  //    order.setNumber(amount);
  //    order.setTxFee(BigDecimal.ZERO);
  //    order.setRealNumber(realNumber);
  //    order.setUid(uid);
  //    order.setUpdateDate(new Date());
  //    // 生成提现订单
  //    order.setStatus(WithdrawCoinOrderStatus.SUCCESS);
  //
  //    if (withdrawCoinOrderUserMapper.insertSelective(order) < 1) {
  //      log.info("用户转出订单添加失败,uid:{},assetCode{},amount{}", uid, assetCode, amount);
  //      throw new AppException(CommonCodeConst.SERVICE_ERROR, "订单添加失败");
  //    }
  //
  //    // 用户扣款
  //    deductMoney(order);
  //
  //  }
  //
  //  @Override
  //  public BigDecimal getUserDailyWithdrawedCoinValue(Integer uid, String assetCode, Date beginDate,
  //      Date endDate) {
  //    return withdrawCoinOrderUserMapper
  //        .getUserDailyWithdrawedCoinValue(uid, assetCode, beginDate, endDate);
  //  }
  //
  //  @Override
  //  public Integer getAmountOfAssetWithStatus(String assetCode, WithdrawCoinOrderStatus status) {
  //    try {
  //      return withdrawCoinOrderUserMapper.getAmountOfAssetWithStatus(assetCode, status);
  //    } catch (Exception e) {
  //      log.error("查询assetCode{},status{}状态数量失败", assetCode, status);
  //      throw new AppException(CommonCodeConst.SERVICE_ERROR, status + "状态订单数量查询失败");
  //    }
  //  }

	/**
	 * 提现审核拒绝
	 * @param dto
   */
	void withdrawRefuse(WithdrawCoinDetailDto dto);

	/**
	 * 云平台充值回调
	 */
	void withdrawConfirmCallback(WithdrawCallbackDto t);
}
