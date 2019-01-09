package com.gop.mapper.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by wuyanjie on 2018/4/26.
 */
@Data
public class FinanceAmountDto {
    private String assetCode;

    private BigDecimal amountAvailable;

    private BigDecimal amountLock;

    private BigDecimal amountTotal;
}
