package com.gop.domain;

import java.math.BigDecimal;
import java.util.Date;

public class UserLockPositionTotalReward {
    private Integer id;

    private String assetCode;

    private BigDecimal totalLockAmount;

    private BigDecimal totalRewardAmount;

    private Integer rewardYear;

    private Integer rewardMonth;

    private Date createDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAssetCode() {
        return assetCode;
    }

    public void setAssetCode(String assetCode) {
        this.assetCode = assetCode;
    }

    public BigDecimal getTotalLockAmount() {
        return totalLockAmount;
    }

    public void setTotalLockAmount(BigDecimal totalLockAmount) {
        this.totalLockAmount = totalLockAmount;
    }

    public BigDecimal getTotalRewardAmount() {
        return totalRewardAmount;
    }

    public void setTotalRewardAmount(BigDecimal totalRewardAmount) {
        this.totalRewardAmount = totalRewardAmount;
    }

    public Integer getRewardYear() {
        return rewardYear;
    }

    public void setRewardYear(Integer rewardYear) {
        this.rewardYear = rewardYear;
    }

    public Integer getRewardMonth() {
        return rewardMonth;
    }

    public void setRewardMonth(Integer rewardMonth) {
        this.rewardMonth = rewardMonth;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}