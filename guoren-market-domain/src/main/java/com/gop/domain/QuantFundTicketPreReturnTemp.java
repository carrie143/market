package com.gop.domain;

import java.math.BigDecimal;
import java.util.Date;

public class QuantFundTicketPreReturnTemp {
    private Integer id;

    private Integer uid;

    private String fundAssetCode;

    private String lockAssetCode;

    private BigDecimal lockAmount;

    private Date createDate;

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

    public String getFundAssetCode() {
        return fundAssetCode;
    }

    public void setFundAssetCode(String fundAssetCode) {
        this.fundAssetCode = fundAssetCode;
    }

    public String getLockAssetCode() {
        return lockAssetCode;
    }

    public void setLockAssetCode(String lockAssetCode) {
        this.lockAssetCode = lockAssetCode;
    }

    public BigDecimal getLockAmount() {
        return lockAmount;
    }

    public void setLockAmount(BigDecimal lockAmount) {
        this.lockAmount = lockAmount;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}