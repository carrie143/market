package com.gop.currency.withdraw.gateway.service.okpay.service.impl;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

import org.apache.axis.AxisFault;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.gop.code.consts.CommonCodeConst;
import com.gop.currency.withdraw.gateway.service.GetWayPayService;
import com.gop.currency.withdraw.gateway.service.okpay.configure.Configurer;
import com.gop.currency.withdraw.gateway.service.okpay.dto.entity.TransactionInfo;
import com.gop.currency.withdraw.gateway.service.okpay.proxy.OkPayAPIProxy;
import com.gop.currency.withdraw.gateway.service.okpay.service.OkPayService;
import com.gop.currency.withdraw.gateway.service.okpay.util.SecurityTokenUtil;
import com.gop.domain.ChannelOkpayOrderWithdraw;
import com.gop.domain.enums.OkpayStatus;
import com.gop.exception.AppException;
import com.gop.mapper.ChannelOkpayOrderWithdrawMapper;
import com.gop.util.OrderUtil;

import lombok.extern.slf4j.Slf4j;

@Service("okPayService")
@Slf4j
public class OkPayServiceImpl implements OkPayService, GetWayPayService<ChannelOkpayOrderWithdraw> {

	@Autowired
	private OkPayAPIProxy proxy;

	@Autowired
	private ChannelOkpayOrderWithdrawMapper channelOkpayOrderWithdrawMapper;

	@Override
	public TransactionInfo pay(String accountNo, String currency, BigDecimal amount, String remark, String invoice) {

		String walletId = Configurer.walletId;
		String password = Configurer.password;
		// String walletId = config.getWalletId();
		// String password = config.getPassword();
		String securityToken = SecurityTokenUtil.getSecurityToken(password);
		log.info("wallet id : " + walletId);
		TransactionInfo info = null;
		try {
			log.info("securityToken id : " + securityToken);
			log.info("accountNo id : " + accountNo);
			log.info("currency id : " + currency);
			log.info("amount id : " + amount);
			log.info("remark id : " + remark);
			log.info("invoice id : " + invoice);
			info = proxy.send_Money(walletId, securityToken, accountNo, currency, amount, remark, false, invoice);
		} catch (RemoteException e) {
			if (e instanceof AxisFault && e.getMessage().equals("Duplicate_Payment")) {
				log.error("okpay 支付订单重复发送 : " + e);
				info = new TransactionInfo();
				info.setComment("Duplicate_Payment");
			} else {
				log.error("okpay 支付失败 : " + e);
				throw new AppException(e.getMessage());
			}
		}
		return info;
	}

	@Override
	public TransactionInfo query(Long transcationId, String invoice) {
		String walletId = Configurer.walletId;
		String password = Configurer.password;
		// String walletId = config.getWalletId();
		// String password = config.getPassword();
		String securityToken = SecurityTokenUtil.getSecurityToken(password);
		TransactionInfo info = null;
		try {
			info = proxy.transaction_Get(walletId, securityToken, transcationId, invoice);
		} catch (RemoteException e) {
			log.error("okpay 查询失败");
			e.printStackTrace();
		}
		return info;
	}

	@Override
	@Transactional
	public String takeOrder(String innerOrder, String accoutNo, String accoutName, String currency, BigDecimal amount,
			String msg) {

		ChannelOkpayOrderWithdraw order = channelOkpayOrderWithdrawMapper.getLastOrderByTxId(innerOrder);

		if (order != null && !order.getTransferStatus().equals(OkpayStatus.FAILURE)) {
			return order.getPayNo();
		}

		if (null == currency || currency.trim().length() == 0) {
			throw new AppException(CommonCodeConst.SERVICE_ERROR, "资产类型错误");
		}

		String payNo = OrderUtil.generateCode(OrderUtil.PAY_SERVICE, OrderUtil.TRANSFER_OUT_CURRENCY);

		ChannelOkpayOrderWithdraw okpay = new ChannelOkpayOrderWithdraw();
		okpay.setAccountName(accoutName);
		okpay.setTxid(innerOrder);
		okpay.setPayNo(payNo);
		okpay.setCurrencyCode(currency);
		okpay.setAccountNo(accoutNo);
		okpay.setAmount(amount);
		okpay.setTransferUsage(msg);
		okpay.setAccountType("0");// 默认只支持银行卡支付
		okpay.setTransferStatus(OkpayStatus.INITIAL);

		okpay.setCreateDate(new Date());
		okpay.setUpdateDate(new Date());
		try {
			channelOkpayOrderWithdrawMapper.insert(okpay);
		} catch (DuplicateKeyException e) {
			log.info("订单号重复" + e);
			throw e;
		} catch (Exception e) {
			log.info("合众支付订单支付失败", e);
			throw new AppException(CommonCodeConst.SERVICE_ERROR, "合众支付订单支付失败");
		}

		return payNo;
	}

	@Override
	@Transactional
	public boolean updateOrder(ChannelOkpayOrderWithdraw order) {
		return channelOkpayOrderWithdrawMapper.updateByPrimaryKeySelective(order) > 0 ? true : false;
	}

	@Override
	public ChannelOkpayOrderWithdraw getOrderById(Integer id) {
		return channelOkpayOrderWithdrawMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<ChannelOkpayOrderWithdraw> getOrdersByStatus(String transferStatus, int limit) {
		PageHelper.offsetPage(0, limit);
		PageHelper.orderBy("id asc");
		return channelOkpayOrderWithdrawMapper.getOrdersByStatus(transferStatus);
	}

	@Override
	public ChannelOkpayOrderWithdraw getOrderForUpdate(int key) {
		return channelOkpayOrderWithdrawMapper.selectForUpdate(key);
	}

	@Override
	public List<ChannelOkpayOrderWithdraw> getRecords(String txid) {
		return channelOkpayOrderWithdrawMapper.getOkpayListByTxid(txid);
	}

	@Override
	public String getLastStatusByTxId(String txid) {
		return channelOkpayOrderWithdrawMapper.getLastStatusByTxId(txid);
	}

	@Override
	public ChannelOkpayOrderWithdraw getLastOrderByTxId(String txid) {
		return channelOkpayOrderWithdrawMapper.getLastOrderByTxId(txid);
	}

	@Override
	public boolean updateOrderByVersion(ChannelOkpayOrderWithdraw order) {
		return channelOkpayOrderWithdrawMapper.updateByVersion(order) > 0 ? true : false;
	}

}
