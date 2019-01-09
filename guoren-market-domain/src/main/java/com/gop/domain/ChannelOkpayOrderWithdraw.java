package com.gop.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.gop.domain.enums.OkpayStatus;

import lombok.Data;

@Data
public class ChannelOkpayOrderWithdraw {
    private Integer id;

    private String txid;

    private String payNo;

    private String accountNo;

    private String accountName;

    private String accountType;

    private String currencyCode;

    private OkpayStatus transferStatus;

    private BigDecimal amount;

    private String transferUsage;

    private BigDecimal fee;

    private String serialNo;

    private Date createDate;

    private Date updateDate;

    private Integer version = 1;

}