package com.gop.currency.transfer.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.gop.domain.DepositMatchBankOrderUser;
import com.gop.domain.enums.RechargeStatus;

public interface DepositMatchBankOrderUserService {

	public void insert(DepositMatchBankOrderUser bankStatement);

	public DepositMatchBankOrderUser query(Integer id);

	public void update(DepositMatchBankOrderUser bankStatement);

	public List<DepositMatchBankOrderUser> queryBankStatement(Date beginTime, Date endTime, BigDecimal minAmount,
			BigDecimal maxAmount, RechargeStatus status, String name, String accountNo, Integer pageNo,
			Integer pageSize);

	public int unrelevanceBySerialNumber(String bankSerialNumber);

	public int confirmBySerialNumber(String orderSerialNumber);

	public DepositMatchBankOrderUser querylock(Integer id);

}
