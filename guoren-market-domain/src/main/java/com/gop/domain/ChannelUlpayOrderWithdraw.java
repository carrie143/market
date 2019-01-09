package com.gop.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.gop.domain.enums.CurrencyType;
import com.gop.domain.enums.UlPayStatus;

import lombok.Data;

@Data
public class ChannelUlpayOrderWithdraw {
    private Integer no;

    private String txNo;

    private String payNo;

    private String bankName;

    private String sn;

    private String bankCode;

    private UlPayStatus status;

    private String accountType;

    private String accountNo;

    private BigDecimal amount;

    private String accountName;

    private String reqSn;

    private String currency;

    private String remark;

    private String province;

    private String city;

    private String eUseCode;

    private String accountProp;

    private String protocol;

    private String protocolUserid;

    private String idType;

    private String id;

    private String tel;

    private String reckonAccount;

    private String custUserid;

    private String reserve1;

    private String reserve2;

    private String reserve3;

    private String reserve4;

    private String eleBankno;

    private String abs;

    private String ps;

    private String uses;

    private String creValDate;

    private String creCvn2;

    private String accPass;

    private Date createDate;

    private Date updateDate;

    private Integer version = 1;

}