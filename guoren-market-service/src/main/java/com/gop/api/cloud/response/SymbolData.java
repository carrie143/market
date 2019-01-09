package com.gop.api.cloud.response;

import lombok.Data;

/**
 * Created by Lxa on 2018/6/15.
 *
 * @author lixianan
 */
@Data
public class SymbolData {
  private Long id;
  private String tradeAsset;
  private String priceAsset;
  private String state;

}
