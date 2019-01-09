package com.gop.coin.transfer.dto;

import java.math.BigDecimal;

import lombok.Data;

/**
 * Created by YAO on 2018/7/9.
 */
@Data
public class WithdrawCallback {
  private String brokerId;

  private Long uid;

  private String assetCode;

  private String clientOrderNo;

  private String coinAddress;

  private BigDecimal amount;

  private BigDecimal realNumber;

  private BigDecimal fee;

  private String status;

  private String message;

  private Long nanoTime;
}
