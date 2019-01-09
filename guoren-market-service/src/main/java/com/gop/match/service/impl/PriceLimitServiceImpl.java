package com.gop.match.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Joiner;
import com.gop.code.consts.CommonCodeConst;
import com.gop.code.consts.OrderCodeConst;
import com.gop.domain.enums.TradeCoinFlag;
import com.gop.exception.AppException;
import com.gop.match.service.PriceLimitService;

import lombok.extern.slf4j.Slf4j;

@Service("PriceLimitService")
@Slf4j
public class PriceLimitServiceImpl implements PriceLimitService {

	@Autowired
	private RedisTemplate<String, String> template;

	@Override
	public void limitBuy(String symbol, BigDecimal price, TradeCoinFlag tradeCoinFlag) {
		int precision = 4;// 获取返回信息数字精度
		
		// 获取24小时前的成交价
		Object price24 = template.opsForHash().entries(Joiner.on(":").join(symbol, "common")).get("24Price");
		if (price24 == null || price24.toString().trim().isEmpty()) {
			log.error("获取24小时前成交价格失败");
			price24 = "10000";
		}

		// 根据24小时的成交价获取最高价格
		BigDecimal pMax =new BigDecimal((String) price24).multiply(new BigDecimal("1.2"));
		if (pMax == null) {
			log.info("获取限价配置失败，请检查配置文件");
			throw new AppException(CommonCodeConst.SERVICE_ERROR, "获取24时前价格异常");
		}
		switch (tradeCoinFlag) {
		case FIXED:
			if (price.compareTo(pMax) > 0) {
				log.info("挂单价格异常：当前最高买入挂单价格{}元", pMax.setScale(precision, BigDecimal.ROUND_DOWN));

				throw new AppException(OrderCodeConst.ORDER_SUPER_DADILY_MAX_PRICE,
						"当前最高买入挂单价格:" + pMax.setScale(precision, BigDecimal.ROUND_DOWN) + "元",
						pMax.setScale(precision, BigDecimal.ROUND_DOWN).toString());
			}
			break;
		case MARKET:
			List<String> orders = template.opsForList().range(Joiner.on(":").join(symbol, "orderd"), 0, 0);
			JSONObject json = null;
			String priceNew = null;
			if (orders.size() > 0) {
				json = JSONObject
						.parseObject(template.opsForList().range(Joiner.on(":").join(symbol, "orderd"), 0, 0).get(0));
				log.info("orderd : " + json.toJSONString());
				priceNew = json.getString("price");
				log.info("priceNew : " + priceNew);
				if (null == priceNew || priceNew.trim().length() == 0) {
					log.error("获取最新成交价格失败");
					priceNew = "0";// 如果没有最新成交价，取消限制
				}
			} else {
				log.error("获取最新成交价格失败");
				priceNew = "0";// 如果没有最新成交价，取消限制
			}
			BigDecimal pNew = new BigDecimal(priceNew);
			if (pNew.compareTo(pMax) > 0)
				throw new AppException(OrderCodeConst.HARDEN_FORBID, "涨停熔断中");
			break;
		default:
			log.info("下单类型错误: 未知下单类型");
			throw new AppException(OrderCodeConst.UNHANDLE_ORDER_TYPE, "未知下单类型");
		}
	}

	@Override
	public void limitSell(String symbol, BigDecimal price, TradeCoinFlag type) {

		int precision = 4;// 获取返回信息数字精度

		// 获取24小时前的成交价
		Object price24 = template.opsForHash().entries(Joiner.on(":").join(symbol, "common")).get("24Price");
		if (price24 == null || price24.toString().trim().isEmpty()) {
			log.error("获取24小时前成交价格失败");
			price24 = "0";
		}
		// 根据24小时的成交价获取最低价格
		BigDecimal pMin = new BigDecimal((String) price24).multiply(new BigDecimal("0.8"));
		if (pMin == null) {
			log.info("获取限价配置失败，请检查配置文件");
			throw new AppException(CommonCodeConst.SERVICE_ERROR, "获取24时前价格异常");
		}
		switch (type) {
		case FIXED:
			if (price.compareTo(pMin) < 0) {
				log.info("挂单价格异常：当前最低卖出挂单价格{}元", pMin.setScale(precision, BigDecimal.ROUND_DOWN));
				throw new AppException(OrderCodeConst.ORDER_LESS_DADILY_MIN_PRICE,
						"当前最低卖出挂单价格：" + pMin.setScale(precision, BigDecimal.ROUND_DOWN) + "元",
						pMin.setScale(precision, BigDecimal.ROUND_DOWN).toString());
			}
			break;
		case MARKET:
			List<String> orders = template.opsForList().range(Joiner.on(":").join(symbol, "orderd"), 0, 0);
			JSONObject json = null;
			String priceNew = null;
			if (orders.size() > 0) {
				json = JSONObject
						.parseObject(template.opsForList().range(Joiner.on(":").join(symbol, "orderd"), 0, 0).get(0));
				priceNew = json.getString("price");
				if (null == priceNew || priceNew.trim().length() == 0) {
					log.info("获取最新成交价格失败");
					priceNew = String.valueOf(Long.MAX_VALUE);// 如果没有最新成交价，取消限制
				}
			} else {
				log.error("获取最新成交价格失败");
				priceNew = String.valueOf(Long.MAX_VALUE);
				;// 如果没有最新成交价，取消限制
			}
			BigDecimal pNew = new BigDecimal(priceNew.trim());
			if (pNew.compareTo(pMin) < 0) {
				log.info("zuixin ");
				throw new AppException(OrderCodeConst.LIMIT_FOARBID, "跌停熔断中");
			}
			break;
		default:
			log.info("下单类型错误: 未知下单类型");
			throw new AppException(OrderCodeConst.UNHANDLE_ORDER_TYPE, "未知下单类型");
		}
	}

}