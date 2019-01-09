package com.gop.domain;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;
@Data
public class TokenOrderCompletionCount {
    private Integer id;

    private Integer uid;

    private BigDecimal sellCount = BigDecimal.ZERO;

    private BigDecimal buyCount = BigDecimal.ZERO;

    private BigDecimal totalCount = BigDecimal.ZERO;

    private Date createTime;

    private Date updateTime;

   
}