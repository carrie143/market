package com.gop.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.gop.domain.enums.DepositCoinAssetStatus;
import com.gop.domain.enums.DestAddressType;

import lombok.Data;

@Data
public class DepositCoinOrderUser {
    private Integer id;

    private Integer uid;

    private Integer brokerId;
    
    private Integer accountId;

    private String account;

    private String assetCode;

    private Integer channelDepositId;

    private String coinAddress;

    private String outerOrderNo;

    private String innerOrderNo;

    private BigDecimal number;

    private BigDecimal realNumber;

    private DestAddressType destAddressType;

    private String msg;

    private Date createDate;

    private Date updateDate;

    private BigDecimal fee;

    private Integer adminId;

    private DepositCoinAssetStatus assetStatus;

}