package com.gop.api.cloud.response;

import lombok.Data;
import lombok.ToString;

/**
 * Created by YAO on 2018/8/1.
 */
@Data
@ToString
public class SymbolScale {
  private Integer amountScale;
  private Integer priceScale;
  private String priceAsset;
  private String tradeAsset;
}
