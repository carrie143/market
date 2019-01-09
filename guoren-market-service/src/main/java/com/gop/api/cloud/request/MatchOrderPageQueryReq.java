package com.gop.api.cloud.request;

import lombok.Data;

/**
 * Created by Lxa on 2018/6/26.
 *
 * @author lixianan
 */
@Data
public class MatchOrderPageQueryReq {
  private Long nanoTime;
  private Long brokerUid;
  private String tradeAsset;
  private String priceAsset;
  private Integer pageNo;
  private Integer pageSize;

}
