package com.gop.domain;

import java.math.BigDecimal;
import java.util.Date;

public class ForkLockPositionDetail {
    private Integer id;

    private Integer uid;

    private Integer planId;

    private String lockAssetCode;

    private BigDecimal lockNum;

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

    public String getLockAssetCode() {
        return lockAssetCode;
    }

    public void setLockAssetCode(String lockAssetCode) {
        this.lockAssetCode = lockAssetCode;
    }

    public BigDecimal getLockNum() {
        return lockNum;
    }

    public void setLockNum(BigDecimal lockNum) {
        this.lockNum = lockNum;
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