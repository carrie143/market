package com.gop.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.gop.domain.enums.MatchState;

import lombok.Data;
@Data
public class DepositMatchCurrencyOrderUser {
    private Integer id;

    private String rechargeName;

    private String rechargeBank;

    private String rechargeAccountNo;

    private BigDecimal rechargeAmount;

    private Date rechargeTime;

    private String rechargeRemark;

    private String orderName;

    private String orderBank;

    private String orderAccountNo;

    private BigDecimal orderAmount;

    private Date orderTime;

    private String orderRemark;

    private Integer createrUser;

    private Integer linkUid;

    private Integer confirmAdminId;

    private MatchState status;

    private String orderSerialNumber;

    private String bankSerialNumber;

    private Date createDate;

    private Date updateDate;

    }