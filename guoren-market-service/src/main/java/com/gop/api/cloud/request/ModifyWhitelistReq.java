package com.gop.api.cloud.request;

import lombok.Data;

/**
 * Created by Lxa on 2018/6/26.
 *
 * @author lixianan
 */
@Data
public class ModifyWhitelistReq {
  private Long nanoTime;
  private Long brokerUid;
  private Boolean whiteList;

}
