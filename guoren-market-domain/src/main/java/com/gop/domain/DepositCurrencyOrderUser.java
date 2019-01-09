package com.gop.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.gop.domain.enums.DepositCurrencyPayMode;
import com.gop.domain.enums.DepositCurrencyOrderStatus;
import com.gop.domain.enums.WithdrawCurrencyOrderStatus;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class DepositCurrencyOrderUser {
    private Integer id;

    private Integer uid;

    private Integer brokerId;
    
    private Integer accountId;

    private String account;
    
    private String assetCode;

    private Integer adminId;

    private BigDecimal money;

    private BigDecimal pay;

    private BigDecimal fee;

    private String txid;

    private String acnumber;

    private String bank;

    private String name;

    private String thirdAccount;

    private String thirdAccountName;
    
    private String thirdAccountCode;

    private DepositCurrencyOrderStatus status;

    private DepositCurrencyPayMode payMode;

    private String msg;

    private Date createDate;

    private Date updateDate;

}