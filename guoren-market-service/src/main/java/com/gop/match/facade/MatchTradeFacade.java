package com.gop.match.facade;

import java.math.BigDecimal;

public interface MatchTradeFacade {
	/**
	 * jsonObject = message; buyOrderNo = jsonObject.getString("buyTxno");
	 * CheckStateUtil.checkArgumentState(null == buyOrderNo, "buyOrderNo is
	 * null"); sellOrderNo = jsonObject.getString("sellTxno");
	 * CheckStateUtil.checkArgumentState(null == sellOrderNo, "sellOrderNo is
	 * null"); buyId = jsonObject.getInteger("buyId");
	 * CheckStateUtil.checkArgumentState(null == buyId, "buyId is null"); sellId
	 * = jsonObject.getInteger("sellId"); CheckStateUtil.checkArgumentState(null
	 * == sellId, "sellId is null"); num = jsonObject.getBigDecimal("num");
	 * CheckStateUtil.checkArgumentState(null == num, "num is null"); price =
	 * jsonObject.getBigDecimal("price"); CheckStateUtil.checkArgumentState(null
	 * == price, "price is null"); symbol = jsonObject.getString("symbol");
	 */
	public void matchRecord(String buyRequestNo, String sellRequestNo, Integer buyId, Integer sellId, BigDecimal num,
			BigDecimal price, String symbol);
}
