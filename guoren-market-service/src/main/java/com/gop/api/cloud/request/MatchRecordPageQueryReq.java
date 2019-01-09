package com.gop.api.cloud.request;

import lombok.Data;

/**
 * Created by Lxa on 2018/6/26.
 *
 * @author lixianan
 */
@Data
public class MatchRecordPageQueryReq {
  private Long nanoTime;
  private Long brokerUid;
  private String orderNo;
  private String tradeAsset;
  private String priceAsset;
  private Integer pageNo;
  private Integer pageSize;

}
