package com.gop.rank.service;

import com.gop.domain.TradeRank;

public interface TradeRankConfigService {

	public TradeRank getTradeRankConfig(String symbol);

	public void updateTradeRankCofig(TradeRank tradeRankConfig);

}
