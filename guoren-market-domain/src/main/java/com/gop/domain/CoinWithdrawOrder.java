package com.gop.domain;

import com.gop.domain.enums.DestAddressType;
import com.gop.domain.enums.WithdrawCoinOrderStatus;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

/**
 * Created by YAO on 2018/7/6.
 */
@Data
public class CoinWithdrawOrder {
  private Integer id;

  private Integer uid;

  private String assetCode;

  private String orderNo;

  private BigDecimal number;

  private BigDecimal realNumber;

  private BigDecimal txFee;

  private Date createDate;

  private Date updateDate;
}
