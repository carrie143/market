package com.gop.currency.transfer.service;

import java.util.List;

import com.gop.domain.DepositMatchCurrencyOrderUser;
import com.gop.domain.enums.MatchState;

public interface DepositMatchCurrencyOrderUserService {

	public void insert(DepositMatchCurrencyOrderUser matchTransferCny);

	public void update(DepositMatchCurrencyOrderUser matchTransferCny);

	public boolean hasMatch(String serNo);

	public List<DepositMatchCurrencyOrderUser> getMatchOrderByStatus(MatchState status, Integer pageSize, Integer pageNo,String name,String accountNo);

	public void confirm(Integer id, Integer adminId);

	public void match(Integer orderId, Integer bankId, Integer adminId);

	public void cancleMatch(Integer id, Integer uid);

}
