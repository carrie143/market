package com.gop.api.cloud.request;

import lombok.Data;

/**
 * Created by YAO on 2018/6/30.
 */
@Data
public class BrokerAssetRequest {
  private String brokerId;
  private Integer pageNo;
  private Integer pageSize;
  private Long nanoTime;
}
