package com.gop.api.cloud.request;

import lombok.Data;

/**
 * Created by Lxa on 2018/6/26.
 *
 * @author lixianan
 */
@Data
public class SymbolFeeAddReq {
  private Long nanoTime;
  private String tradeAsset;
  private String priceAsset;
  private BrokerSymbolFee.Method method;
  private String feeMin;
  private String makerFeeRatio;
  private String takerFeeRatio;
}
