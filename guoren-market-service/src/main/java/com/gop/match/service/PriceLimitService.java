package com.gop.match.service;

import java.math.BigDecimal;

import com.gop.domain.enums.TradeCoinFlag;
import com.gop.exception.PriceException;

public interface PriceLimitService {
	/**
	 * 对买单进行价格限制。禁止高价买
	 * 
	 * @param price
	 * @param type
	 * @throws PriceException
	 */
	public void limitBuy(String symbol, BigDecimal price, TradeCoinFlag type);

	/**
	 * 对买单进行价格限制。禁止低价卖
	 * 
	 * @param price
	 * @param type
	 * @throws PriceException
	 */
	public void limitSell(String symbol, BigDecimal price, TradeCoinFlag type);

}
