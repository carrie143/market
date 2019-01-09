package com.gop.currency.transfer.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.gop.code.consts.CommonCodeConst;
import com.gop.code.consts.RechargeCodeConst;
import com.gop.currency.transfer.service.DepositCurrencyQueryOrderService;
import com.gop.currency.transfer.service.DepositCurrencyService;
import com.gop.currency.transfer.service.DepositMatchBankOrderUserService;
import com.gop.currency.transfer.service.DepositMatchCurrencyOrderUserService;
import com.gop.domain.DepositCurrencyOrderUser;
import com.gop.domain.DepositMatchBankOrderUser;
import com.gop.domain.DepositMatchCurrencyOrderUser;
import com.gop.domain.enums.DepositCurrencyOrderStatus;
import com.gop.domain.enums.MatchState;
import com.gop.domain.enums.RechargeStatus;
import com.gop.exception.AppException;
import com.gop.mapper.DepositMatchCurrencyOrderUserMapper;

@Service
public class DepositMatchCurrencyOrderUserServiceImpl implements DepositMatchCurrencyOrderUserService {

	@Autowired
	private DepositMatchCurrencyOrderUserMapper depositMatchCurrencyOrderUserMapper;

	@Autowired
	private DepositCurrencyService depositCurrencyService;

	@Autowired
	private DepositMatchBankOrderUserService depositMatchBankOrderUserService;
	@Autowired
	DepositCurrencyQueryOrderService depositCurrencyQueryOrderService;

	@Override
	public void insert(DepositMatchCurrencyOrderUser matchTransferCny) {
		throw new AppException(CommonCodeConst.SERVICE_ERROR);
	}

	@Override
	public void update(DepositMatchCurrencyOrderUser matchTransferCny) {
		throw new AppException(CommonCodeConst.SERVICE_ERROR);

	}

	@Override
	public boolean hasMatch(String serNo) {

		throw new AppException(CommonCodeConst.SERVICE_ERROR);
	}

	@Override
	public List<DepositMatchCurrencyOrderUser> getMatchOrderByStatus(MatchState status, Integer pageSize,
			Integer pageNo, String name, String accountNo) {

		PageHelper.startPage(pageNo, pageSize);
		PageHelper.orderBy("create_date desc");

		List<DepositMatchCurrencyOrderUser> lists = depositMatchCurrencyOrderUserMapper.getByStatus(status, name,
				accountNo);
		return lists;
	}

	@Override
	@Transactional
	public void confirm(Integer id, Integer adminId) {
		DepositMatchCurrencyOrderUser depositMatchCurrencyOrderUser = depositMatchCurrencyOrderUserMapper
				.selectByPrimaryKeyLock(id);
		if (null == depositMatchCurrencyOrderUser) {
			throw new AppException(RechargeCodeConst.MATCH_ORDER_NOT_EXIST);
		}
		if (!depositMatchCurrencyOrderUser.getStatus().equals(MatchState.UNCONFIRM)) {
			throw new AppException(RechargeCodeConst.MATCH_STATE_ERROR);
		}
		depositMatchBankOrderUserService.confirmBySerialNumber(depositMatchCurrencyOrderUser.getBankSerialNumber());
		depositMatchCurrencyOrderUser.setConfirmAdminId(adminId);
		depositMatchCurrencyOrderUser.setStatus(MatchState.CONFIRM);
		depositMatchCurrencyOrderUserMapper.updateByPrimaryKeySelective(depositMatchCurrencyOrderUser);
		depositCurrencyService.confirm(depositMatchCurrencyOrderUser.getOrderSerialNumber(), adminId,
				depositMatchCurrencyOrderUser.getRechargeAmount());
	}

	@Override
	@Transactional
	public void match(Integer orderId, Integer bankId, Integer adminId) {

		DepositMatchBankOrderUser depositMatchBankOrderUser = depositMatchBankOrderUserService.query(bankId);
		DepositCurrencyOrderUser depositCurrencyOrderUser = depositCurrencyQueryOrderService.getOrder(orderId);

		if (null == depositMatchBankOrderUser || null == depositMatchBankOrderUser) {
			throw new AppException(RechargeCodeConst.RECHARGE_ORDER_NOT_EXIST);
		}

		depositCurrencyService.changeStatus(depositCurrencyOrderUser.getTxid(), DepositCurrencyOrderStatus.WAIT,
				DepositCurrencyOrderStatus.PROCESSING);

		Integer count = depositMatchCurrencyOrderUserMapper
				.getEffectiveBySerialNumber(depositMatchBankOrderUser.getSerialNumber());
		if (null != count && !count.equals(0)) {
			throw new AppException(RechargeCodeConst.RECHARGE_ORDER_HAS_MATCH);
		}
		depositMatchBankOrderUser.setStatus(RechargeStatus.RELEVANCE);
		depositMatchBankOrderUser.setEditerAdminId(adminId);
		depositMatchBankOrderUserService.update(depositMatchBankOrderUser);

		DepositMatchCurrencyOrderUser depositMatchCurrencyOrderUser = new DepositMatchCurrencyOrderUser();
		depositMatchCurrencyOrderUser.setBankSerialNumber(depositMatchBankOrderUser.getSerialNumber());
		depositMatchCurrencyOrderUser.setLinkUid(adminId);
		depositMatchCurrencyOrderUser.setCreateDate(new Date());
		depositMatchCurrencyOrderUser.setCreaterUser(depositMatchBankOrderUser.getEditerAdminId());

		depositMatchCurrencyOrderUser.setOrderAccountNo(depositCurrencyOrderUser.getAcnumber());
		depositMatchCurrencyOrderUser.setOrderAmount(depositCurrencyOrderUser.getMoney());
		depositMatchCurrencyOrderUser.setOrderBank(depositCurrencyOrderUser.getBank());
		depositMatchCurrencyOrderUser.setOrderName(depositCurrencyOrderUser.getName());
		depositMatchCurrencyOrderUser.setOrderRemark(depositCurrencyOrderUser.getMsg());
		depositMatchCurrencyOrderUser.setOrderSerialNumber(depositCurrencyOrderUser.getTxid());
		depositMatchCurrencyOrderUser.setOrderTime(depositCurrencyOrderUser.getCreateDate());

		depositMatchCurrencyOrderUser.setRechargeAccountNo(depositMatchBankOrderUser.getAccountNo());
		depositMatchCurrencyOrderUser.setRechargeBank(depositMatchBankOrderUser.getSource());
		depositMatchCurrencyOrderUser.setRechargeName(depositMatchBankOrderUser.getName());
		depositMatchCurrencyOrderUser.setRechargeRemark(depositMatchBankOrderUser.getPostscript());
		depositMatchCurrencyOrderUser.setRechargeAmount(depositMatchBankOrderUser.getMoney());
		depositMatchCurrencyOrderUser.setRechargeTime(depositMatchBankOrderUser.getCreateDate());
		depositMatchCurrencyOrderUser.setBankSerialNumber(depositMatchBankOrderUser.getSerialNumber());

		depositMatchCurrencyOrderUser.setStatus(MatchState.UNCONFIRM);
		depositMatchCurrencyOrderUser.setUpdateDate(new Date());

		depositMatchCurrencyOrderUserMapper.insertSelective(depositMatchCurrencyOrderUser);

	}

	@Override
	@Transactional
	public void cancleMatch(Integer id, Integer uid) {
		DepositMatchCurrencyOrderUser depositMatchCurrencyOrderUser = depositMatchCurrencyOrderUserMapper
				.selectByPrimaryKeyLock(id);
		depositMatchCurrencyOrderUser.setLinkUid(uid);
		depositMatchCurrencyOrderUser.setStatus(MatchState.INVALID);
		depositCurrencyService.changeStatus(depositMatchCurrencyOrderUser.getOrderSerialNumber(),
				DepositCurrencyOrderStatus.PROCESSING, DepositCurrencyOrderStatus.WAIT);
		depositMatchCurrencyOrderUserMapper.updateByPrimaryKey(depositMatchCurrencyOrderUser);
		depositMatchBankOrderUserService.unrelevanceBySerialNumber(depositMatchCurrencyOrderUser.getBankSerialNumber());

	}

}
