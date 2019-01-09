package com.gop.currency.transfer.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.gop.domain.WithdrawCurrencyOrderUser;
import com.gop.domain.enums.WithdrawCurrencyOrderStatus;
import com.gop.domain.enums.WithdrawCurrencyPayMode;

public interface WithdrawCurrencyService {

	public void withdrawByChannel(int id, String payMode, int adminId);

	public void withdrawByOffline(int id, int adminId);

	public void cancel(int id, int adminid);

	public void refund(int id, int adminId);

	public void lock(int id, int adminId);

	public void unlock(int id, int adminId);

	public PageInfo<WithdrawCurrencyOrderUser> queryOrder(Integer uid, String assetCode, Integer pageNo,
			Integer PageSize);

	public PageInfo<WithdrawCurrencyOrderUser> queryOrder(Integer id, Integer brokerId, Integer uId, String innerOrderNo,String account,
			String name, WithdrawCurrencyOrderStatus status, Integer pageNo, Integer pageSize);

	public WithdrawCurrencyOrderUser getOrder(int id);

	public WithdrawCurrencyOrderUser getOrder(int uid, String outerOrderNo);

	public void confirmOffline(Integer id, Integer adminId);

	public void confirm(WithdrawCurrencyOrderUser order);

	public void toUnknown(WithdrawCurrencyOrderUser order);

	public List<WithdrawCurrencyOrderUser> getTransferCnyList(WithdrawCurrencyOrderStatus status,
			WithdrawCurrencyPayMode payMode, int limit);

	//
	// public void reject(int id, int adminId);

}
