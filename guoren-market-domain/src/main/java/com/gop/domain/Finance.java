package com.gop.domain;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Finance {
    private Integer id;

    private Integer uid;
    
    private Integer brokerId;

    private String accountNo;

    private String accountKind;

    private String assetCode;

    private BigDecimal amountAvailable;

    private BigDecimal amountLock;

    private BigDecimal amountLoan;

    private Date updateDate;

    private Integer version;

}