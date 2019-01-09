package com.gop.domain;

import java.util.Date;

public class TradeRank {
    private Integer id;

    private Date beginTime;

    private Date endTime;

    private String symbol;

    private Integer lastTradeMatchResultId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Integer getLastTradeMatchResultId() {
        return lastTradeMatchResultId;
    }

    public void setLastTradeMatchResultId(Integer lastTradeMatchResultId) {
        this.lastTradeMatchResultId = lastTradeMatchResultId;
    }
}