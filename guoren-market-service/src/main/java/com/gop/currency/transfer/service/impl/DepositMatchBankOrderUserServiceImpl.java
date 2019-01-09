package com.gop.currency.transfer.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.gop.code.consts.RechargeCodeConst;
import com.gop.currency.transfer.service.DepositMatchBankOrderUserService;
import com.gop.domain.DepositMatchBankOrderUser;
import com.gop.domain.enums.RechargeStatus;
import com.gop.exception.AppException;
import com.gop.mapper.DepositMatchBankOrderUserMapper;

import lombok.extern.log4j.Log4j;

@Service("bankStatementServiceImpl")
@Log4j
public class DepositMatchBankOrderUserServiceImpl implements DepositMatchBankOrderUserService {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private DepositMatchBankOrderUserMapper depositMatchBankOrderUserMapper;

	@Override
	public void insert(DepositMatchBankOrderUser bankStatement) {
		depositMatchBankOrderUserMapper.insertSelective(bankStatement);
	}

	@Override
	public DepositMatchBankOrderUser query(Integer id) {

		return depositMatchBankOrderUserMapper.selectByPrimaryKey(id);
	}

	@Override
	public DepositMatchBankOrderUser querylock(Integer id) {

		return depositMatchBankOrderUserMapper.selectByPrimaryKeyLock(id);
	}

	@Override
	public void update(DepositMatchBankOrderUser bankStatement) {
		depositMatchBankOrderUserMapper.updateByPrimaryKeySelective(bankStatement);
	}

	@Override
	public List<DepositMatchBankOrderUser> queryBankStatement(Date beginTime, Date endTime, BigDecimal minAmount,
			BigDecimal maxAmount, RechargeStatus status, String name, String accountNo, Integer pageNo,
			Integer pageSize) {
		Map<String, Object> map = new HashMap<>();
		map.put("beginTime", beginTime);
		map.put("endTime", endTime);
		map.put("minAmount", minAmount);
		map.put("maxAmount", maxAmount);
		map.put("status", status);
		if (null != name) {
			map.put("name", "%" + name.trim() + "%");
		}
		if (null != accountNo) {
			map.put("accountNo", "%" + accountNo.trim() + "%");
		}

		if (pageNo == null) {
			pageNo = 1;
		}
		if (pageSize == null) {
			pageSize = 10;
		}
		PageHelper.startPage(pageNo, pageSize, true);
		PageHelper.orderBy("update_date desc");
		List<DepositMatchBankOrderUser> lists = null;
		try {
			lists = depositMatchBankOrderUserMapper.queryBankStatement(map);
		} catch (Exception e) {
			log.error("查询出错:" + e);
			return null;
		}
		return lists;
	}

	@Override
	public int unrelevanceBySerialNumber(String bankSerialNumber) {
		int num = depositMatchBankOrderUserMapper.unrelevanceBySerialNumber(bankSerialNumber);
		if (num == 0) {
			throw new AppException(RechargeCodeConst.RECHARGE_ORDER_STATE_CHANGED);
		}
		return num;
	}

	@Override
	public int confirmBySerialNumber(String orderSerialNumber) {
		int num = depositMatchBankOrderUserMapper.confirmBySerialNumber(orderSerialNumber);
		if (num == 0) {
			throw new AppException(RechargeCodeConst.RECHARGE_ORDER_STATE_CHANGED);
		}
		return num;
	}

}
