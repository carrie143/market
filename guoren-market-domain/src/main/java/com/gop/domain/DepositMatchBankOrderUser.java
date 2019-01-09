package com.gop.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.gop.domain.enums.RechargeStatus;

import lombok.Data;

@Data
public class DepositMatchBankOrderUser {
    private Integer id;

    private String serialNumber;

    private BigDecimal money;

    private String name;

    private String bankName;

    private String accountNo;

    private String postscript;

    private RechargeStatus status;

    private String source;

    private Integer editerAdminId;

    private Date createDate;

    private Date updateDate;

    private Date insertDate;

}