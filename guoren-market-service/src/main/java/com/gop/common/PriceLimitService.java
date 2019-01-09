package com.gop.common;

import com.gop.domain.enums.TradeCoinFlag;
import com.gop.exception.PriceException;

public interface PriceLimitService {
	
	/**
	 * 对买单进行价格限制。禁止高价买
	 * @param price
	 * @param type
	 * @throws PriceException
	 */
	public void limitBuy(String price,TradeCoinFlag type) throws PriceException;
	
	
	/**
	 * 对买单进行价格限制。禁止低价卖
	 * @param price
	 * @param type
	 * @throws PriceException
	 */
	public void limitSell(String price, TradeCoinFlag type) throws PriceException;

}
