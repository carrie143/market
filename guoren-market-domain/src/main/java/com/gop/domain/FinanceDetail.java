package com.gop.domain;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class FinanceDetail {
    private Integer id;

    private Integer uid;

    private String assetCode;

    private String requestNo;

    private String businessSubject;
    
    private Integer assetChangeType;  

//    private Integer subject1;
//
//    private Integer subject2;

    private BigDecimal amountAvailable;

    private BigDecimal amountLock;

    private BigDecimal amountLoan;

    private BigDecimal balanceOldAvailable;

    private BigDecimal balanceOldLock;

    private BigDecimal balanceOldLoan;

    private BigDecimal balanceNewAvailable;

    private BigDecimal balanceNewLock;

    private BigDecimal balanceNewLoan;

    private Date createDate;

}