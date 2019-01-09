package com.gop.domain;

import java.util.Date;

public class ChannelAlipayOrderDeposit {
    private Integer id;

    private String accountNo;

    private Date recordedDate;

    private String transactionNo;

    private String orderNo;

    private String businessNo;

    private String transferType;

    private String income;

    private String expenditure;

    private String accountBalance;

    private String serviceCharge;

    private String paymentChannels;

    private String contractProduct;

    private String reciprocalAccount;

    private String reciprocalName;

    private String bankOrder;

    private String productName;

    private String remark;

    private Date createDate;

    private String fileName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo == null ? null : accountNo.trim();
    }

    public Date getRecordedDate() {
        return recordedDate;
    }

    public void setRecordedDate(Date recordedDate) {
        this.recordedDate = recordedDate;
    }

    public String getTransactionNo() {
        return transactionNo;
    }

    public void setTransactionNo(String transactionNo) {
        this.transactionNo = transactionNo == null ? null : transactionNo.trim();
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public String getBusinessNo() {
        return businessNo;
    }

    public void setBusinessNo(String businessNo) {
        this.businessNo = businessNo == null ? null : businessNo.trim();
    }

    public String getTransferType() {
        return transferType;
    }

    public void setTransferType(String transferType) {
        this.transferType = transferType == null ? null : transferType.trim();
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income == null ? null : income.trim();
    }

    public String getExpenditure() {
        return expenditure;
    }

    public void setExpenditure(String expenditure) {
        this.expenditure = expenditure == null ? null : expenditure.trim();
    }

    public String getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(String accountBalance) {
        this.accountBalance = accountBalance == null ? null : accountBalance.trim();
    }

    public String getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(String serviceCharge) {
        this.serviceCharge = serviceCharge == null ? null : serviceCharge.trim();
    }

    public String getPaymentChannels() {
        return paymentChannels;
    }

    public void setPaymentChannels(String paymentChannels) {
        this.paymentChannels = paymentChannels == null ? null : paymentChannels.trim();
    }

    public String getContractProduct() {
        return contractProduct;
    }

    public void setContractProduct(String contractProduct) {
        this.contractProduct = contractProduct == null ? null : contractProduct.trim();
    }

    public String getReciprocalAccount() {
        return reciprocalAccount;
    }

    public void setReciprocalAccount(String reciprocalAccount) {
        this.reciprocalAccount = reciprocalAccount == null ? null : reciprocalAccount.trim();
    }

    public String getReciprocalName() {
        return reciprocalName;
    }

    public void setReciprocalName(String reciprocalName) {
        this.reciprocalName = reciprocalName == null ? null : reciprocalName.trim();
    }

    public String getBankOrder() {
        return bankOrder;
    }

    public void setBankOrder(String bankOrder) {
        this.bankOrder = bankOrder == null ? null : bankOrder.trim();
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }
}