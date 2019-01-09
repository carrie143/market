package com.gop.domain;

import java.math.BigDecimal;
import java.util.Date;

public class ForkLockPositionPlan {
    private Integer id;

    private String lockAssetCode;

    private String targetAssetCode;

    private BigDecimal minLockNum;

    private BigDecimal maxLockNum;

    private BigDecimal exchangeRate;

    private Date beginDate;

    private Date endDate;

    private Date unlockDate;

    private String status;

    private Date createDate;

    private Date updateDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLockAssetCode() {
        return lockAssetCode;
    }

    public void setLockAssetCode(String lockAssetCode) {
        this.lockAssetCode = lockAssetCode;
    }

    public String getTargetAssetCode() {
        return targetAssetCode;
    }

    public void setTargetAssetCode(String targetAssetCode) {
        this.targetAssetCode = targetAssetCode;
    }

    public BigDecimal getMinLockNum() {
        return minLockNum;
    }

    public void setMinLockNum(BigDecimal minLockNum) {
        this.minLockNum = minLockNum;
    }

    public BigDecimal getMaxLockNum() {
        return maxLockNum;
    }

    public void setMaxLockNum(BigDecimal maxLockNum) {
        this.maxLockNum = maxLockNum;
    }

    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getUnlockDate() {
        return unlockDate;
    }

    public void setUnlockDate(Date unlockDate) {
        this.unlockDate = unlockDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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