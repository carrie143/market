package com.gop.api.cloud.request;

import lombok.Data;

/**
 * Created by Lxa on 2018/6/26.
 *
 * @author lixianan
 */
@Data
public class SymbolQueryReq {
  private Long nanoTime;
  private String[] coin;
}
