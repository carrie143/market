package com.gop.api.cloud.request;

import com.gop.domain.enums.DepositCoinAssetStatus;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

/**
 * Created by YAO on 2018/7/9.
 */
@Data
public class DepositCallbackDto {
  private String brokerId;
  private Long uid;
  private String assetCode;
  private String toWallet;
  private BigDecimal amount;
  private DepositCoinAssetStatus status;
  private String message;
  private Date finishDate;  //最后处理时间
}