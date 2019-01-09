package com.gop.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.gop.domain.enums.DestAddressType;
import com.gop.domain.enums.WithdrawCoinOrderStatus;

import lombok.Data;

@Data
public class WithdrawCoinOrderUser {
    private Integer id;

    private Integer uid;

    private Integer brokerId;
    
    private Integer accountId;

    private String account;
    
    private String assetCode;

    private Integer channelWithdrawId;

    private String coinAddress;

    private String outerOrderNo;

    private String innerOrderNo;

    private BigDecimal number;

    private BigDecimal realNumber;

    private WithdrawCoinOrderStatus status;

    private DestAddressType destAddressType;

    private String msg;

    private Date createDate;

    private Date updateDate;

    private BigDecimal txFee;

    private Integer adminId;

}