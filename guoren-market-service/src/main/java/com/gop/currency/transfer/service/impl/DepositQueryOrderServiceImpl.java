package com.gop.currency.transfer.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gop.common.Environment;
import com.gop.currency.transfer.service.DepositCurrencyQueryOrderService;
import com.gop.domain.DepositCurrencyOrderUser;
import com.gop.domain.enums.WithdrawCurrencyOrderStatus;
import com.gop.mapper.DepositCurrencyOrderUserMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DepositQueryOrderServiceImpl implements DepositCurrencyQueryOrderService {

	@Autowired
	Environment environmentContxt;

	@Autowired
	private DepositCurrencyOrderUserMapper depositCurrencyOrderUserMapper;
	

	@Override
	public PageInfo<DepositCurrencyOrderUser> queryOrder(Integer uid, String assetCode, Integer pageNo,
			Integer pageSize) {
		PageHelper.startPage(pageNo, pageSize);
		PageHelper.orderBy("create_date desc");
		return new PageInfo<>(depositCurrencyOrderUserMapper.getDepositListByAssetcode(uid, assetCode));
	}

	@Override
	public PageInfo<DepositCurrencyOrderUser> queryOrder(Integer id, Integer brokerId, Integer uId, String orderNo, String account,
			String name,String acnumber, WithdrawCurrencyOrderStatus status, Integer pageNo, Integer pageSize) {
		PageHelper.startPage(pageNo, pageSize);
		PageHelper.orderBy("create_date desc");
		return new PageInfo<>(
				depositCurrencyOrderUserMapper.getDepositOrderList(brokerId, id, uId, orderNo, account, name, acnumber,status));
	}

	@Override
	public DepositCurrencyOrderUser getOrder(int id) {
		return depositCurrencyOrderUserMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<DepositCurrencyOrderUser> queryLastThrityOrder() {

		return depositCurrencyOrderUserMapper.selectLastThrityOrderByWaitAndSup3d();
	}
	

	@Override
	public List<DepositCurrencyOrderUser> queryOrder(Integer uId, String acnumber, WithdrawCurrencyOrderStatus status) {

		return depositCurrencyOrderUserMapper.getDepositOrderList(null, null, uId, null, null,null ,acnumber, status);
	}
}
