package com.gop.domain;

public class BrokerNotify {
    private Integer id;

    private Integer brokerId;

    private String symbol;

    private String coinTransferout;

    private String coinTransferin;

    private String currencyTransferout;

    private String tradeCallback;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBrokerId() {
        return brokerId;
    }

    public void setBrokerId(Integer brokerId) {
        this.brokerId = brokerId;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol == null ? null : symbol.trim();
    }

    public String getCoinTransferout() {
        return coinTransferout;
    }

    public void setCoinTransferout(String coinTransferout) {
        this.coinTransferout = coinTransferout == null ? null : coinTransferout.trim();
    }

    public String getCoinTransferin() {
        return coinTransferin;
    }

    public void setCoinTransferin(String coinTransferin) {
        this.coinTransferin = coinTransferin == null ? null : coinTransferin.trim();
    }

    public String getCurrencyTransferout() {
        return currencyTransferout;
    }

    public void setCurrencyTransferout(String currencyTransferout) {
        this.currencyTransferout = currencyTransferout == null ? null : currencyTransferout.trim();
    }

    public String getTradeCallback() {
        return tradeCallback;
    }

    public void setTradeCallback(String tradeCallback) {
        this.tradeCallback = tradeCallback == null ? null : tradeCallback.trim();
    }
}