package com.gop.domain;

import java.math.BigDecimal;

import lombok.Data;
import lombok.ToString;

/**
 * Created by YAO on 2018/4/26.
 */
@Data
@ToString
public class WalletInOutTotal {
  private String assetCode;
  private BigDecimal depositAmount;
  private BigDecimal withDrawAmount;
  private BigDecimal preAmount;
  private BigDecimal totalAmount;
}
