package com.gop.domain;

import java.math.BigDecimal;
import java.util.Date;

public class ForkGrantAssetDetail {
    private Integer id;

    private Integer uid;

    private Integer planId;

    private String targetAssetCode;

    private BigDecimal exchangeNum;

    private String requestNo;

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

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public String getTargetAssetCode() {
        return targetAssetCode;
    }

    public void setTargetAssetCode(String targetAssetCode) {
        this.targetAssetCode = targetAssetCode;
    }

    public BigDecimal getExchangeNum() {
        return exchangeNum;
    }

    public void setExchangeNum(BigDecimal exchangeNum) {
        this.exchangeNum = exchangeNum;
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