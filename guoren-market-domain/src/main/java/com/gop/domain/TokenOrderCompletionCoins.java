package com.gop.domain;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;
@Data
public class TokenOrderCompletionCoins {
    private Integer id;

    private String assetName;

    private BigDecimal completionNum = BigDecimal.ZERO;

    private Date createTime;

    private Date updateTime;

    
}