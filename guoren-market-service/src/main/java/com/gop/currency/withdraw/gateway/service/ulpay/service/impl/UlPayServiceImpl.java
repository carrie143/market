//package com.gop.currency.withdraw.gateway.service.ulpay.service.impl;
//
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.commons.lang.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.dao.DuplicateKeyException;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.alibaba.fastjson.JSONObject;
//import com.github.pagehelper.PageHelper;
//import com.gop.code.consts.CommonCodeConst;
//import com.gop.currency.withdraw.gateway.service.GetWayPayService;
//import com.gop.currency.withdraw.gateway.service.ulpay.common.Constants;
//import com.gop.currency.withdraw.gateway.service.ulpay.configure.Configurer;
//import com.gop.currency.withdraw.gateway.service.ulpay.dto.RequestBatch;
//import com.gop.currency.withdraw.gateway.service.ulpay.dto.RequestQuery;
//import com.gop.currency.withdraw.gateway.service.ulpay.dto.ResponseBatch;
//import com.gop.currency.withdraw.gateway.service.ulpay.dto.body.RequestBatchBody;
//import com.gop.currency.withdraw.gateway.service.ulpay.dto.body.RequestQueryBody;
//import com.gop.currency.withdraw.gateway.service.ulpay.dto.body.detail.RequestBatchDetail;
//import com.gop.currency.withdraw.gateway.service.ulpay.dto.body.detail.RequestQueryDetail;
//import com.gop.currency.withdraw.gateway.service.ulpay.dto.body.sum.RequestBatchSum;
//import com.gop.currency.withdraw.gateway.service.ulpay.dto.info.RequestBatchInfo;
//import com.gop.currency.withdraw.gateway.service.ulpay.dto.info.RequestQueryInfo;
//import com.gop.currency.withdraw.gateway.service.ulpay.service.UlPayService;
//import com.gop.currency.withdraw.gateway.service.ulpay.util.GZipUtil;
//import com.gop.currency.withdraw.gateway.service.ulpay.util.HttpUtils;
//import com.gop.currency.withdraw.gateway.service.ulpay.util.KeyGeneratorUtil;
//import com.gop.currency.withdraw.gateway.service.ulpay.util.SignatureUtil;
//import com.gop.currency.withdraw.gateway.service.ulpay.util.XmlUtil;
//import com.gop.domain.ChannelUlpayBank;
//import com.gop.domain.ChannelUlpayOrderWithdraw;
//import com.gop.domain.enums.UlPayStatus;
//import com.gop.exception.AppException;
//import com.gop.exception.UlpayBankNullException;
//import com.gop.mapper.ChannelUlpayBankMapper;
//import com.gop.mapper.ChannelUlpayOrderWithdrawMapper;
//import com.gop.util.BankUtil;
//import com.gop.util.DateTimeUtil;
//import com.gop.util.OrderUtil;
//
//import lombok.extern.slf4j.Slf4j;
//
//@Service("ulPayService")
//@Slf4j
//public class UlPayServiceImpl implements UlPayService, GetWayPayService<ChannelUlpayOrderWithdraw> {
//
//	Logger logger = LoggerFactory.getLogger(this.getClass());
//
//	@Autowired
//	private ChannelUlpayOrderWithdrawMapper channelUlpayOrderWithdrawMapper;
//
//	@Autowired
//	private ChannelUlpayBankMapper channelUlpayBankMapper;
//
//	@Override
//	public Map<String, Object> batchPay(RequestBatch requestData) {
//		Map<String, Object> ret = new HashMap<String, Object>();
//		logger.info("send order to ulpay!!!");
//		try {
//			String xml = XmlUtil.toXML(requestData);
//			// 加签
// 			xml = SignatureUtil.addSignatrue(xml);
//			// 加压加密
//			String Base64 = GZipUtil.gzipString(xml);
//			// 通讯使用HTTPS进行通讯
//			String response1 = HttpUtils.sendPostMessage(Base64, Configurer.batchPayUrl, Constants.transfer_charset);
//			// 解压解密返回信息
//			String response2 = null;
//			if (StringUtils.isNotEmpty(response1)) {
//				response2 = GZipUtil.ungzipString(response1);
//				boolean isSuc = SignatureUtil.isSignature(response2);
//				if (isSuc) {
//					ret.put("signMsg", "返回数据验签成功！！");
//				} else {
//					ret.put("signMsg", "返回数据验签失败！！");
//					logger.error("合众支付返回数据验签失败！！请检查验签文件！");
//				}
//				// 将解压解密后的结果转化为ResponseBatch对象
//				ResponseBatch responseBatch = XmlUtil.fromXML(response2, ResponseBatch.class);
//
//				if ("0000".equals(responseBatch.getInfo().getRetCode())) {
//					ret.put("retStatus", true);
//					ret.put("retCode", responseBatch.getInfo().getRetCode());
//					ret.put("retMsg", responseBatch.getInfo().getErrMsg());
//					ret.put("retXml", response2);
//				} else {
//					ret.put("retStatus", false);
//					ret.put("retCode", responseBatch.getInfo().getRetCode());
//					ret.put("retMsg", responseBatch.getInfo().getErrMsg());
//					ret.put("retXml", response2);
//				}
//			} else {
//				ret.put("retStatus", false);
//				ret.put("retMsg", "付款接口连接失败");
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return ret;
//	}
//
//	@Override
//	public Map<String, Object> batchPayQuery(RequestQuery resquestData) {
//		Map<String, Object> ret = new HashMap<>();
//		logger.info("send order to ulpay!!!");
//		try {
//			String xml = XmlUtil.toXML(resquestData);
//			// 加签
//			xml = SignatureUtil.addSignatrue(xml);
//			// 加压加密
//			String Base64 = GZipUtil.gzipString(xml);
//			// 通讯使用HTTPS进行通讯
//			String response1 = HttpUtils.sendPostMessage(Base64, Configurer.payQueryUrl, Constants.transfer_charset);
//
//			String response2 = null;
//			if (StringUtils.isNotEmpty(response1)) {
//
//				response2 = GZipUtil.ungzipString(response1);
//
//				boolean isSuc = SignatureUtil.isSignature(response2);
//				if (isSuc) {
//					ret.put("signMsg", "返回数据验签成功！！");
//				} else {
//					ret.put("signMsg", "返回数据验签失败！！");
//					logger.error("合众支付返回数据验签失败！！请检查验签文件！");
//				}
//
//				// 将解压解密后的结果转化为ResponseBatch对象
//				ResponseBatch responseBatch = XmlUtil.fromXML(response2, ResponseBatch.class);
//
//				if ("0000".equals(responseBatch.getInfo().getRetCode())) {
//					ret.put("retStatus", true);
//					ret.put("retCode", responseBatch.getInfo().getRetCode());
//					ret.put("retDetailCode", responseBatch.getBody().getRetDetail().get(0).getRetCode());
//					ret.put("retMsg", responseBatch.getBody().getRetDetail().get(0).getErrMsg());
//					ret.put("retXml", response2);
//				} else {
//					ret.put("retStatus", false);
//					ret.put("retCode", responseBatch.getInfo().getRetCode());
//					ret.put("retMsg", responseBatch.getInfo().getErrMsg());
//					ret.put("retXml", response2);
//				}
//			} else {
//				ret.put("retStatus", false);
//				ret.put("retMsg", "付款接口连接失败");
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return ret;
//	}
//
//	@Override
//	public Map<String, Object> generalBatchPay(String reqSn, String innerOrder, String bankName, String bankNo,
//			String bankCode, String accountName, BigDecimal amount, String msg) {
//
//		RequestBatchInfo info = new RequestBatchInfo();
//		info.setTrxCode(Configurer.batchPayCode);
//		info.setVersion(Configurer.defaultVersion);
//		info.setDataType(Configurer.INFO_DATA_TYPE);
//		info.setLevel(Configurer.LEVEL);
//		info.setUserName(Configurer.userName);
//		info.setUserPass(Configurer.userPassword);
//		info.setReqSn(reqSn);
//		info.setSignedMsg("");
//
//		RequestBatchDetail detail = new RequestBatchDetail();
//		detail.setSn(innerOrder);
//		detail.setAccountName(accountName);
//		detail.setAccountNo(bankNo);
//		detail.setBankName(bankName);
//		detail.setBankCode(bankCode);
//		detail.setAmount(amount.setScale(2, BigDecimal.ROUND_DOWN).toString());
//		detail.setAccountProp("0");
//		detail.setCurrency("CNY");
//		detail.setAccountType("00");
//		detail.setRemark(msg);
//
//		List<RequestBatchDetail> details = new ArrayList<>();
//		details.add(detail);
//
//		RequestBatchSum sum = new RequestBatchSum();
//		JSONObject cardInfo = BankUtil.getCardInfo(bankNo);
//		switch (cardInfo.get("cardType").toString()) {
//		case "SAVINGS_DEPOSIT_CARD":
//			sum.setBusinessCode("09100");
//			break;
//		case "CREDIT_CARD":
//			sum.setBusinessCode("09300");
//			break;
//		default:
//			throw new AppException(CommonCodeConst.SERVICE_ERROR, "不支持银行卡");
//		}
//		sum.setMerchantId(Configurer.merchantId);
//		sum.setSendTime(DateTimeUtil.getDateTime());
//		sum.setSendDt(DateTimeUtil.getDate());
//		sum.setTotalSum(amount.setScale(2, BigDecimal.ROUND_DOWN).toString());
//		sum.setTotalItem("1");
//
//		RequestBatchBody body = new RequestBatchBody();
//		body.setTransDetails(details);
//		body.setTransSum(sum);
//
//		RequestBatch requestData = new RequestBatch();
//		requestData.setBody(body);
//		requestData.setInfo(info);
//
//		return batchPay(requestData);
//	}
//
//	@Override
//	@Transactional
//	public Map<String, Object> generalBatchQuery(String reqSn, String payNo) {
//		RequestQueryInfo info = new RequestQueryInfo();
//
//		info.setTrxCode(Configurer.batchQueryCode);
//		info.setVersion(Configurer.defaultVersion);
//		info.setDataType(Configurer.INFO_DATA_TYPE);
//		info.setReqSn(reqSn);
//		info.setUserName(Configurer.userName);
//		info.setUserPass(Configurer.userPassword);
//		info.setSignedMsg("");
//		info.setMerchantId(Configurer.merchantId);
//
//		RequestQueryDetail detail = new RequestQueryDetail();
//		// detail.setQuerySn(payNo);
//
//		RequestQueryBody body = new RequestQueryBody();
//		body.setRetDetail(detail);
//
//		RequestQuery requestData = new RequestQuery();
//		requestData.setBody(body);
//		requestData.setInfo(info);
//
//		return batchPayQuery(requestData);
//	}
//
//	@Override
//	@Transactional
//	public String takeOrder(String innerOrder, String accountNo, String accoutName, String currency, BigDecimal amount,
//			String msg) {
//		ChannelUlpayOrderWithdraw order = channelUlpayOrderWithdrawMapper.getUlPayByTxNo(innerOrder);
//
//		if (order != null && !order.getStatus().equals(UlPayStatus.FAILURE)) {
//			return order.getPayNo();
//		}
//
//		if (null == currency || currency.trim().length() == 0) {
//			throw new AppException(CommonCodeConst.SERVICE_ERROR, "资产类型错误");
//		}
//
//		String payNo = OrderUtil.generateCode(OrderUtil.PAY_SERVICE, OrderUtil.TRANSFER_OUT_CURRENCY);
//
//		ChannelUlpayOrderWithdraw ulpay = new ChannelUlpayOrderWithdraw();
//		ulpay.setAccountName(accoutName);
//
//		ulpay.setTxNo(innerOrder);
//		ulpay.setPayNo(payNo);
//		ulpay.setCurrency(currency);
//		ulpay.setAccountNo(accountNo);
//		ulpay.setSn(innerOrder);
//		ulpay.setAmount(amount);
//		ulpay.setRemark(msg);
//		ulpay.setAccountType("0");// 默认只支持银行卡支付
//		ulpay.setStatus(UlPayStatus.INITIAL);
//		ulpay.setReqSn(KeyGeneratorUtil.generatorReqSn());
//		String bankName = BankUtil.getBankName(accountNo);
//
//		if (null == bankName) {
//			log.error("合众支付提现卡号错误，无法识别卡号{}的银行名称", accountNo);
//			throw new AppException(CommonCodeConst.SERVICE_ERROR);
//		}
//
//		ChannelUlpayBank bank = channelUlpayBankMapper.getBankByName(bankName);
//		if (null == bank) {
//			logger.error("通过合众支付银行列表无法获取银行代码");
//			throw new UlpayBankNullException("银行名错误！");
//		}
//
//		ulpay.setBankCode(bank.getId());
//		ulpay.setBankName(bankName);
//
//		ulpay.setCreateDate(new Date());
//		ulpay.setUpdateDate(new Date());
//		try {
//			channelUlpayOrderWithdrawMapper.insert(ulpay);
//		} catch (DuplicateKeyException e) {
//			log.info("订单号重复" + e);
//			throw e;
//		} catch (Exception e) {
//			log.info("超级代付订单支付失败", e);
//			throw new AppException(CommonCodeConst.SERVICE_ERROR, "超级代付订单支付失败");
//		}
//		return payNo;
//	}
//
//	@Override
//	@Transactional
//	public boolean updateOrder(ChannelUlpayOrderWithdraw order) {
//		return channelUlpayOrderWithdrawMapper.updateByPrimaryKeySelective(order) > 0 ? true : false;
//	}
//
//	@Override
//	public ChannelUlpayOrderWithdraw getOrderById(Integer id) {
//		return channelUlpayOrderWithdrawMapper.selectByPrimaryKey(id);
//	}
//
//	@Override
//	public List<ChannelUlpayOrderWithdraw> getOrdersByStatus(String transferStatus, int limit) {
//		PageHelper.offsetPage(0, limit);
//		PageHelper.orderBy("id asc");
//		return channelUlpayOrderWithdrawMapper.getUlPayListByStatus(transferStatus);
//	}
//
//	@Override
//	public ChannelUlpayOrderWithdraw getOrderForUpdate(int key) {
//		return channelUlpayOrderWithdrawMapper.selectForUpdate(key);
//	}
//
//	@Override
//	public List<ChannelUlpayOrderWithdraw> getRecords(String txid) {
//		return channelUlpayOrderWithdrawMapper.getUlPayListByTxNo(txid);
//	}
//
//	@Override
//	public String getLastStatusByTxId(String txid) {
//		return channelUlpayOrderWithdrawMapper.getLastOrderByTxId(txid).getStatus().toString();
//	}
//
//	@Override
//	public ChannelUlpayOrderWithdraw getLastOrderByTxId(String txid) {
//		return channelUlpayOrderWithdrawMapper.getLastOrderByTxId(txid);
//	}
//
//	@Override
//	public boolean updateOrderByVersion(ChannelUlpayOrderWithdraw order) {
//		return channelUlpayOrderWithdrawMapper.updateByWithVersion(order) > 0 ? true : false;
//	}
//
//}
