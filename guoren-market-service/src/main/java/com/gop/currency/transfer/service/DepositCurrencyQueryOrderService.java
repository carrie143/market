package com.gop.currency.transfer.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.gop.domain.DepositCurrencyOrderUser;
import com.gop.domain.enums.WithdrawCurrencyOrderStatus;

public interface DepositCurrencyQueryOrderService {

	public PageInfo<DepositCurrencyOrderUser> queryOrder(Integer uid, String assetCode, Integer pageNo,
			Integer pageSize);

	public PageInfo<DepositCurrencyOrderUser> queryOrder(Integer id, Integer brokerId, Integer uId, String orderNo, String account,
			String name,String acnumber,WithdrawCurrencyOrderStatus status, Integer pageNo, Integer pageSize);

	public List<DepositCurrencyOrderUser> queryOrder(Integer uId, String account, WithdrawCurrencyOrderStatus status);

	public DepositCurrencyOrderUser getOrder(int id);

	public List<DepositCurrencyOrderUser> queryLastThrityOrder();
	
}
