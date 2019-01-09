package com.gop.api.cloud.response;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class FinanceDetail {
    private Long id;

    private String platId;  //brokerId+uid

    private String brokerId;

    private Long uid;

    private String assetCode;

    private String requestNo;

    private String businessSubject;
    
    private Integer assetChangeType;  

    private BigDecimal amountAvailable;

    private BigDecimal amountLock;

    private BigDecimal balanceOldAvailable;

    private BigDecimal balanceOldLock;

    private BigDecimal balanceNewAvailable;

    private BigDecimal balanceNewLock;

    private Date createDate;

}