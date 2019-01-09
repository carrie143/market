package com.gop.domain;

import java.math.BigDecimal;

import lombok.Data;
import lombok.ToString;

/**
 * Created by YAO on 2018/4/26.
 */
@Data
@ToString
public class WalletBalanceTotal {
  private String assetCode;
  private BigDecimal hotBalance;
  private BigDecimal coldBalance;
  private BigDecimal totalBalance;
}
