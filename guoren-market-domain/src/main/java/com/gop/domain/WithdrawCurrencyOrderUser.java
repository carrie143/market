package com.gop.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.gop.domain.enums.WithdrawCurrencyOrderStatus;
import com.gop.domain.enums.WithdrawCurrencyPayMode;

import lombok.Data;


@Data
public class WithdrawCurrencyOrderUser {
    private Integer id;

    private Integer uid;

    private Integer brokerId;
    
    private Integer accountId;

    private String account;

    private String assetCode;

    private Integer channelWithdrawId;

    private Integer adminId;

    private BigDecimal money;

    private BigDecimal pay;

    private BigDecimal fee;

    private String outerOrderNo;

    private String innerOrderNo;
    
    private String acnumber;

    private String bank;

    private String name;

    private WithdrawCurrencyOrderStatus status;

    private WithdrawCurrencyPayMode payMode;

    private String msg;

    private Date createDate;

    private Date updateDate;
    
}