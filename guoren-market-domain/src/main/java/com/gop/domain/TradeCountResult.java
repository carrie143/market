package com.gop.domain;

import java.math.BigDecimal;

import lombok.Data;

/**
 * Created by YAO on 2018/4/12.
 */
@Data
public class TradeCountResult {

  private BigDecimal number;
  private BigDecimal amount;
  private Long total;
  private BigDecimal buyFee;
  private BigDecimal sellFee;

}
