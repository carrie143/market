package com.gop.domain;

import java.math.BigDecimal;
import java.util.Date;

public class C2cTransOrderRecord {
    private Integer id;

    private String orderId;

    private String transOrderId;

    private Integer buyUid;

    private Integer sellUid;

    private BigDecimal lockNum;

    private BigDecimal transNum;

    private BigDecimal unlockNum;

    private Date createDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTransOrderId() {
        return transOrderId;
    }

    public void setTransOrderId(String transOrderId) {
        this.transOrderId = transOrderId;
    }

    public Integer getBuyUid() {
        return buyUid;
    }

    public void setBuyUid(Integer buyUid) {
        this.buyUid = buyUid;
    }

    public Integer getSellUid() {
        return sellUid;
    }

    public void setSellUid(Integer sellUid) {
        this.sellUid = sellUid;
    }

    public BigDecimal getLockNum() {
        return lockNum;
    }

    public void setLockNum(BigDecimal lockNum) {
        this.lockNum = lockNum;
    }

    public BigDecimal getTransNum() {
        return transNum;
    }

    public void setTransNum(BigDecimal transNum) {
        this.transNum = transNum;
    }

    public BigDecimal getUnlockNum() {
        return unlockNum;
    }

    public void setUnlockNum(BigDecimal unlockNum) {
        this.unlockNum = unlockNum;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}