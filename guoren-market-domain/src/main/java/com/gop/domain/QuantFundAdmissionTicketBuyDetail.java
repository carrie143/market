package com.gop.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.gop.domain.enums.QuantFundTransferType;

public class QuantFundAdmissionTicketBuyDetail {
    private Integer id;

    private Integer ticketId;

    private Integer uid;

    private String fundAssetCode;

    private String lockAssetCode;

    private QuantFundTransferType transferType;

    private BigDecimal amount;

    private String requestNo;

    private Date createDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTicketId() {
        return ticketId;
    }

    public void setTicketId(Integer ticketId) {
        this.ticketId = ticketId;
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

    public QuantFundTransferType getTransferType() {
        return transferType;
    }

    public void setTransferType(QuantFundTransferType transferType) {
        this.transferType = transferType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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