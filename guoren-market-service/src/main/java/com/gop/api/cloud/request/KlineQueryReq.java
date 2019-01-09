package com.gop.api.cloud.request;

import lombok.Data;

/**
 * Created by Lxa on 2018/1/30.
 *
 * @author lixianan
 */
@Data
public class KlineQueryReq {
  private long startTime;
  private long endTime;
  private int pageNum;
  private int pageSize;
  private String symbol;
  private String kline;
  private long nanoTime;
}
