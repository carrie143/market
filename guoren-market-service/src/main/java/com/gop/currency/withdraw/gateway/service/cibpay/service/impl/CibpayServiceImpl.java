package com.gop.currency.withdraw.gateway.service.cibpay.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.gop.code.consts.CommonCodeConst;
import com.gop.currency.withdraw.gateway.service.GetWayPayService;
import com.gop.currency.withdraw.gateway.service.cibpay.config.CIBConfig;
import com.gop.currency.withdraw.gateway.service.cibpay.config.CibOrderStatus;
import com.gop.currency.withdraw.gateway.service.cibpay.config.SignAlgorithm;
import com.gop.currency.withdraw.gateway.service.cibpay.service.CibPayService;
import com.gop.currency.withdraw.gateway.service.cibpay.util.CIBUtils;
import com.gop.currency.withdraw.gateway.service.cibpay.util.Signature;
import com.gop.domain.ChannelCibOrderWithdraw;
import com.gop.exception.AppException;
import com.gop.mapper.ChannelCibBankMapper;
import com.gop.mapper.ChannelCibOrderWithdrawMapper;
import com.gop.util.BankUtil;
import com.gop.util.DateTimeUtil;
import com.gop.util.OrderUtil;

import lombok.extern.slf4j.Slf4j;

@Service("cibPayService")
@Slf4j
public class CibpayServiceImpl implements CibPayService, GetWayPayService<ChannelCibOrderWithdraw> {

	private static final String SERVICE_PAY_NAME = "cib.epay.payment.pay";
	private static final String SERVICE_VER = "02";
	private static final String SERVICE_CUR = "CNY";
	private static final String SERVICE_GET_NAME = "cib.epay.payment.get";

	@Autowired
	private ChannelCibOrderWithdrawMapper channelCibOrderWithdrawMapper;

	@Autowired
	private ChannelCibBankMapper channelCibBankMapper;

	@Override
	public String cibPay(String order_no, String to_bank_no, String to_acct_no, String to_acct_name, String acct_type,
			String trans_amt, String trans_usage) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("order_no", order_no);
		params.put("to_bank_no", to_bank_no);
		params.put("to_acct_no", to_acct_no);
		params.put("to_acct_name", to_acct_name);
		params.put("acct_type", acct_type);
		params.put("trans_amt", trans_amt);
		params.put("trans_usage", trans_usage);

		params.put("appid", CIBConfig.APP_ID);
		params.put("service", SERVICE_PAY_NAME);
		params.put("ver", SERVICE_VER);
		params.put("sub_mrch", CIBConfig.CIB_SUB_MERCHANT_NAME);
		params.put("cur", SERVICE_CUR);
		params.put("timestamp", DateTimeUtil.getDateTime());
		params.put("sign_type", SignAlgorithm.get(SERVICE_PAY_NAME));
		params.put("mac", Signature.generateMAC(params));

		System.out.println(params.toString());
		System.out.println(CIBConfig.PY_API);
		return CIBUtils.txn(CIBConfig.PY_API, params);

	}

	@Override
	public String cibQuery(String order_no) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("order_no", order_no);
		params.put("appid", CIBConfig.APP_ID);
		params.put("service", SERVICE_GET_NAME);
		params.put("ver", SERVICE_VER);
		params.put("timestamp", DateTimeUtil.getDateTime());
		params.put("sign_type", SignAlgorithm.get(SERVICE_GET_NAME));
		params.put("mac", Signature.generateMAC(params));

		return CIBUtils.txn(CIBConfig.PY_API, params);
	}

	@Override
	@Transactional
	public String takeOrder(String innerOrder, String accoutNo, String accoutName, String currency, BigDecimal amount,
			String msg) {

		ChannelCibOrderWithdraw order = channelCibOrderWithdrawMapper.getLastOrderByTxId(innerOrder);

		if (order != null && !order.getTransferStatus().equals(CibOrderStatus.FAILURE)) {
			return order.getPayNo();
		}

		if (null == currency || currency.trim().length() == 0) {
			throw new AppException(CommonCodeConst.SERVICE_ERROR, "资产类型错误");
		}

		String payNo = OrderUtil.generateCode(OrderUtil.PAY_SERVICE, OrderUtil.TRANSFER_OUT_CURRENCY);

		String bankName = BankUtil.getBankName(accoutNo);

		if (null == bankName) {
			log.error("超级代付提现卡号错误，无法识别卡号{}的银行名称", accoutNo);
			throw new AppException(CommonCodeConst.SERVICE_ERROR);
		}

		String bankNo = null;
		if (null == (bankNo = channelCibBankMapper.getBankNoByName(bankName))) {
			if (null == (bankNo = channelCibBankMapper.getBankNoByShortName(bankName))) {
				log.error("超级代付提现卡号错误，无法通过银行名称{}获取数据库中的bankno", bankName);
				throw new AppException(CommonCodeConst.SERVICE_ERROR);
			}
		}

		ChannelCibOrderWithdraw cibOrder = new ChannelCibOrderWithdraw();
		cibOrder.setAccountName(accoutName);
		cibOrder.setBankNo(bankNo);
		cibOrder.setTxid(innerOrder);
		cibOrder.setPayNo(payNo);
		cibOrder.setCurrencyCode(currency);
		cibOrder.setAccountNo(accoutNo);
		cibOrder.setAmount(amount);
		cibOrder.setTransferUsage("超级代付");
		cibOrder.setAccountType("0");// 默认只支持银行卡支付
		cibOrder.setTransferStatus(CibOrderStatus.INITIAL);

		cibOrder.setCreateDate(new Date());
		cibOrder.setUpdateDate(new Date());
		try {
			channelCibOrderWithdrawMapper.insert(cibOrder);
		} catch (DuplicateKeyException e) {
			log.info("订单号重复" + e);
			throw e;
		} catch (Exception e) {
			log.info("超级代付订单支付失败", e);
			throw new AppException(CommonCodeConst.SERVICE_ERROR, "超级代付订单支付失败");
		}

		return payNo;
	}

	@Override
	@Transactional
	public boolean updateOrder(ChannelCibOrderWithdraw order) {
		return channelCibOrderWithdrawMapper.updateByPrimaryKeySelective(order) > 0 ? true : false;
	}

	@Override
	public ChannelCibOrderWithdraw getOrderById(Integer id) {
		return channelCibOrderWithdrawMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<ChannelCibOrderWithdraw> getOrdersByStatus(String transferStatus, int limit) {
		PageHelper.offsetPage(0, limit);
		PageHelper.orderBy("id asc");
		return channelCibOrderWithdrawMapper.getCibOrderByStatus(transferStatus);
	}

	@Override
	public ChannelCibOrderWithdraw getOrderForUpdate(int key) {
		return channelCibOrderWithdrawMapper.selectForUpdate(key);
	}

	@Override
	public List<ChannelCibOrderWithdraw> getRecords(String txid) {

		return channelCibOrderWithdrawMapper.getCibPayListByTxId(txid);
	}

	@Override
	public String getLastStatusByTxId(String txid) {
		return channelCibOrderWithdrawMapper.getLastOrderByTxId(txid).getTransferStatus();
	}

	@Override
	public ChannelCibOrderWithdraw getLastOrderByTxId(String txid) {
		return channelCibOrderWithdrawMapper.getLastOrderByTxId(txid);
	}

	@Override
	public boolean updateOrderByVersion(ChannelCibOrderWithdraw order) {
		return channelCibOrderWithdrawMapper.updateByVersion(order) > 0 ? true : false;
	}

}
