package com.gop.domain;

import java.math.BigDecimal;

import lombok.Data;
import lombok.ToString;

/**
 * Created by YAO on 2018/4/26.
 */
@Data
@ToString
public class FinanceTotal {
  private String assetCode;
  private BigDecimal inoutBalance;
  private BigDecimal walletBalance;
  private BigDecimal assetBalance;
  private BigDecimal depositWithdrawBalance;
  private BigDecimal walletDiff;
  private BigDecimal platWalletDiff;
  private BigDecimal platDiff;
}
