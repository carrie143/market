package com.gop.settlement.controller.dto;

import java.math.BigDecimal;

import lombok.Data;

/**
 * Created by YAO on 2018/6/30.
 */
@Data
public class BrokerWithdrawDto {
  private String assetCode;
  private String address;
  private BigDecimal amount;
}
