package com.gop.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.gop.domain.enums.C2cGoldenTransStatus;

import lombok.Data;
@Data
public class C2cGoldenTransOrder {
    private Integer id;

    private String goldenTradeOrderNo;

    private Integer buyUid;

    private Integer sellUid;

    private String assetCode;

    private BigDecimal number;

    private BigDecimal money;

    private C2cGoldenTransStatus status;

    private Date createDate;

    private Date updateDate;

    
}