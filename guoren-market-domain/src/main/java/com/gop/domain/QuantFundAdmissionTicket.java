package com.gop.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.gop.domain.enums.QuantFundAdmissionTicketStatus;

public class QuantFundAdmissionTicket {
    private Integer id;

    private Integer uid;

    private String fundAssetCode;

    private String lockAssetCode;

    private BigDecimal lockAmount;

    private QuantFundAdmissionTicketStatus status;

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

    public QuantFundAdmissionTicketStatus getStatus() {
        return status;
    }

    public void setStatus(QuantFundAdmissionTicketStatus status) {
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