package com.gop.api.cloud.request;

import com.gop.api.cloud.common.OrderType;
import com.gop.api.cloud.common.TradeType;
import java.math.BigDecimal;
import lombok.Data;

/**
 * Created by Lxa on 2018/6/26.
 *
 * @author lixianan
 */
@Data
public class MatchOrderReq {

  private Long nanoTime;
  private Long brokerUid;
  private String clientOrderNo;
  private TradeType tradeType;
  private OrderType orderType;
  private String tradeAsset;
  private String priceAsset;
  private BigDecimal price;
  private BigDecimal amount;

}
