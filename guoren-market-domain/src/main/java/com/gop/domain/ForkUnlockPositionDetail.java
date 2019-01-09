package com.gop.domain;

import java.math.BigDecimal;
import java.util.Date;

public class ForkUnlockPositionDetail {
    private Integer id;

    private Integer uid;

    private Integer lockDetailId;

    private String unlockAssetCode;

    private BigDecimal unlockNum;

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

    public Integer getLockDetailId() {
        return lockDetailId;
    }

    public void setLockDetailId(Integer lockDetailId) {
        this.lockDetailId = lockDetailId;
    }

    public String getUnlockAssetCode() {
        return unlockAssetCode;
    }

    public void setUnlockAssetCode(String unlockAssetCode) {
        this.unlockAssetCode = unlockAssetCode;
    }

    public BigDecimal getUnlockNum() {
        return unlockNum;
    }

    public void setUnlockNum(BigDecimal unlockNum) {
        this.unlockNum = unlockNum;
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