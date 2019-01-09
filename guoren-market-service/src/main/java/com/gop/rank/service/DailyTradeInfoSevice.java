package com.gop.rank.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.gop.domain.DailyTradeInfo;

public interface DailyTradeInfoSevice {

	public void addDailyTradeNum(int rankId, Integer uid, BigDecimal amount, Date date);

	public Integer getDailyRankByUid(int rankId, Integer uid, Date date);

	public List<DailyTradeInfo> getDailyRank(int rankId, Date date, int limit);

}
