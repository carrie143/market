package com.gop.api.cloud.request;

import lombok.Data;

/**
 * Created by Lxa on 2018/6/26.
 *
 * @author lixianan
 */
@Data
public class CancelOrderReq {

  private Long nanoTime;
  private Long brokerUid;
  private String clientOrderNo;
  private String tradeAsset;
  private String priceAsset;



}
