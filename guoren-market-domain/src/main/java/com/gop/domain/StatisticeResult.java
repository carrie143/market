package com.gop.domain;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by wuyanjie on 2018/5/22.
 */
@Data
public class StatisticeResult {
    private String symbol;   //交易对/币种
    private BigDecimal tradeNumber;  //交易量（24h）/提现充值量（24h）
}
