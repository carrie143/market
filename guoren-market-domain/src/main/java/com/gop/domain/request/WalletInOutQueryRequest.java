package com.gop.domain.request;

import lombok.Builder;
import lombok.Data;

/**
 * Created by YAO on 2018/5/16.
 */
@Data
@Builder
public class WalletInOutQueryRequest {

  private String beginDate;
  private String endDate;
  private String account;
  private String opt;
  private String assetCode;
  private Integer page;
  private Integer pageSize;
}
