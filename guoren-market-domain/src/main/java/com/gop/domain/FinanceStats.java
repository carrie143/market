package com.gop.domain;

import java.math.BigDecimal;
import java.util.Date;

public class FinanceStats {
    private Integer id;

    private String assetCode;

    private String userType;

    private String businessKey;

    private Integer financeDate;

    private String dateUnit;

    private BigDecimal openingBalance;

    private BigDecimal amount;

    private BigDecimal closingBalance;

    private Date updateDate;

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
        this.assetCode = assetCode == null ? null : assetCode.trim();
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType == null ? null : userType.trim();
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey == null ? null : businessKey.trim();
    }

    public Integer getFinanceDate() {
        return financeDate;
    }

    public void setFinanceDate(Integer financeDate) {
        this.financeDate = financeDate;
    }

    public String getDateUnit() {
        return dateUnit;
    }

    public void setDateUnit(String dateUnit) {
        this.dateUnit = dateUnit == null ? null : dateUnit.trim();
    }

    public BigDecimal getOpeningBalance() {
        return openingBalance;
    }

    public void setOpeningBalance(BigDecimal openingBalance) {
        this.openingBalance = openingBalance;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getClosingBalance() {
        return closingBalance;
    }

    public void setClosingBalance(BigDecimal closingBalance) {
        this.closingBalance = closingBalance;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}