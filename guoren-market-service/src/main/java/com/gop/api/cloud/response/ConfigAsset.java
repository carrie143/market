package com.gop.api.cloud.response;

import com.gop.domain.enums.AssetStatus;
import com.gop.domain.enums.CurrencyType;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class ConfigAsset {
  private Long id;

  private String assetCode;

  //资产种类
  private CurrencyType currencyType;

  private AssetStatus status;

  private String name;

  //提现手续费
  private BigDecimal withdrawFee;

  //当前的供应量
  private BigDecimal supplyAmount;

  //总的供应量
  private BigDecimal totalAmount;

  //最小精度
  private Integer minPrecision;

  private String description;

  //官方url
  private String webUrl;

  private Date createDate;

  private Date updateDate;

}