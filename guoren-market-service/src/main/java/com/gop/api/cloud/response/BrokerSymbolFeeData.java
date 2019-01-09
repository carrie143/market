package com.gop.api.cloud.response;

import java.math.BigDecimal;

import lombok.Data;

/**
 * Created by Lxa on 2018/6/15.
 *
 * @author lixianan
 */
@Data
public class BrokerSymbolFeeData {
  private String brokerId;
  private String method;
  private String tradeAsset;
  private String priceAsset;
  private BigDecimal feeMin;
  private BigDecimal feeRatio;
}
