package com.gop.rank.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import com.gop.domain.DailyTradeInfo;

public interface TotalTradeInfoService {

	public void addTotalTradeNum(int rankId, Integer uid, BigDecimal amount);

	public Integer getTotalRankByUid(int rankId, Integer uid);

	public List<DailyTradeInfo> getDailyRank(int rankId, int limit);
}
