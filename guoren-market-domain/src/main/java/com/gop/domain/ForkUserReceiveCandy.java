package com.gop.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.gop.domain.enums.ForkUserReceiveCandyStatus;

public class ForkUserReceiveCandy {
    private Integer id;

    private Integer uid;

    private String forkAssetCode;

    private String targetAssetCode;

    private BigDecimal forkAmount;

    private BigDecimal targetAmount;

    private BigDecimal exchangeRate;

    private ForkUserReceiveCandyStatus status;

    private Date createDate;

    private Date updateDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public ForkUserReceiveCandyStatus getStatus() {
        return status;
    }

    public void setStatus(ForkUserReceiveCandyStatus status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}