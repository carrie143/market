package com.gop.domain;

import java.util.Date;

public class ChannelCoinAddressDepositInfo {
    private Integer id;

    private String addressAssetCode;

    private String targetAssetCode;

    private Date createDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAddressAssetCode() {
        return addressAssetCode;
    }

    public void setAddressAssetCode(String addressAssetCode) {
        this.addressAssetCode = addressAssetCode;
    }

    public String getTargetAssetCode() {
        return targetAssetCode;
    }

    public void setTargetAssetCode(String targetAssetCode) {
        this.targetAssetCode = targetAssetCode;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}