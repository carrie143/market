package com.gop.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.Data;
import lombok.ToString;

/**
 * Created by YAO on 2018/4/24.
 */
@Data
@ToString
public class WalletBalance {
  private Integer id;
  private String assetCode;
  private String account;
  private String walletType;
  private BigDecimal balance;
  private Timestamp createTime;
}