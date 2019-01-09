package com.gop.settlement.controller.dto;

import java.math.BigDecimal;

import lombok.Data;

/**
 * Created by YAO on 2018/7/3.
 */
@Data
public class WithdrawAmountLimitDto {
  private String assetCode;
  private String address;
  private BigDecimal balance;
  private String cloudFee;
  private String withdrawMin;
}
