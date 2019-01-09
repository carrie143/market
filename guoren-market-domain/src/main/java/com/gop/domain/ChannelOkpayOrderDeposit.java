package com.gop.domain;

import java.util.Date;

public class ChannelOkpayOrderDeposit {
    private Integer id;

    private String transactionNo;

    private String accountNo;

    private String accountName;

    private String voucherNo;

    private String currency;

    private String type;

    private String debitAmount;

    private String creditAmount;

    private String accountBalance;

    private String abstractMsg;

    private String reciprocalAccount;

    private String reciprocalName;

    private String reciprocalBank;

    private String reciproalBankingFirm;

    private Date transactionDate;

    private String remark;

    private Date createDate;

    private String fileName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTransactionNo() {
        return transactionNo;
    }

    public void setTransactionNo(String transactionNo) {
        this.transactionNo = transactionNo == null ? null : transactionNo.trim();
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo == null ? null : accountNo.trim();
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName == null ? null : accountName.trim();
    }

    public String getVoucherNo() {
        return voucherNo;
    }

    public void setVoucherNo(String voucherNo) {
        this.voucherNo = voucherNo == null ? null : voucherNo.trim();
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency == null ? null : currency.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getDebitAmount() {
        return debitAmount;
    }

    public void setDebitAmount(String debitAmount) {
        this.debitAmount = debitAmount == null ? null : debitAmount.trim();
    }

    public String getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(String creditAmount) {
        this.creditAmount = creditAmount == null ? null : creditAmount.trim();
    }

    public String getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(String accountBalance) {
        this.accountBalance = accountBalance == null ? null : accountBalance.trim();
    }

    public String getAbstractMsg() {
        return abstractMsg;
    }

    public void setAbstractMsg(String abstractMsg) {
        this.abstractMsg = abstractMsg == null ? null : abstractMsg.trim();
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

    public String getReciprocalBank() {
        return reciprocalBank;
    }

    public void setReciprocalBank(String reciprocalBank) {
        this.reciprocalBank = reciprocalBank == null ? null : reciprocalBank.trim();
    }

    public String getReciproalBankingFirm() {
        return reciproalBankingFirm;
    }

    public void setReciproalBankingFirm(String reciproalBankingFirm) {
        this.reciproalBankingFirm = reciproalBankingFirm == null ? null : reciproalBankingFirm.trim();
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
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