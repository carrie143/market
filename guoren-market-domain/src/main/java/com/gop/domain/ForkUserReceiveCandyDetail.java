package com.gop.domain;

import java.math.BigDecimal;
import java.util.Date;

public class ForkUserReceiveCandyDetail {
    private Integer id;

    private Integer forkCandyId;

    private Integer uid;

    private String forkAssetCode;

    private String targetAssetCode;

    private BigDecimal forkAmount;

    private BigDecimal targetAmount;

    private BigDecimal exchangeRate;

    private String requestNo;

    private Date createDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getForkCandyId() {
        return forkCandyId;
    }

    public void setForkCandyId(Integer forkCandyId) {
        this.forkCandyId = forkCandyId;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getForkAssetCode() {
        return forkAssetCode;
    }

    public void setForkAssetCode(String forkAssetCode) {
        this.forkAssetCode = forkAssetCode;
    }

    public String getTargetAssetCode() {
        return targetAssetCode;
    }

    public void setTargetAssetCode(String targetAssetCode) {
        this.targetAssetCode = targetAssetCode;
    }

    public BigDecimal getForkAmount() {
        return forkAmount;
    }

    public void setForkAmount(BigDecimal forkAmount) {
        this.forkAmount = forkAmount;
    }

    public BigDecimal getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(BigDecimal targetAmount) {
        this.targetAmount = targetAmount;
    }

    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public String getRequestNo() {
        return requestNo;
    }

    public void setRequestNo(String requestNo) {
        this.requestNo = requestNo;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}