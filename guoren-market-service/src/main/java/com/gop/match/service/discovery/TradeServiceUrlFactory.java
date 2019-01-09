package com.gop.match.service.discovery;

public interface TradeServiceUrlFactory {
	public String getUrlByLoadBalance(String symbol);
}
