package com.gop.domain;

import com.gop.domain.enums.OptType;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import lombok.Data;
import lombok.ToString;

/**
 * Created by YAO on 2018/4/25.
 */
@Data
@ToString
public class WalletInOut {
  private String opt;
  private String assetCode;
  private String fromAccount; // address
  private String toAccount; // address
  private BigDecimal amount;
  private String status;
  private Timestamp updateTime;
}
