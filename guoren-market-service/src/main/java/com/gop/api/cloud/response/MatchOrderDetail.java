package com.gop.api.cloud.response;

import com.gop.api.cloud.common.OrderState;
import com.gop.api.cloud.common.OrderType;
import com.gop.api.cloud.common.TradeType;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * Created by Lxa on 2018/6/21.
 *
 * @author lixianan
 */
@Data
public class MatchOrderDetail {

  //订单创建时间
  private Date createTime;
  //撮合数量
  private BigDecimal tradedNumber;
  //剩余数量
  private BigDecimal numberOver;
  //客户订单号
  private String clientOrderNo;
  //平台订单号
  private String platformOrderNo;
  // 下单类型
  private OrderType orderType;
  // 下单种类
  private TradeType tradeType;
  // 交易币种
  private String tradeAssert;
  // 计价币种
  private String priceAssert;
  // 下单价格
  private  BigDecimal price;
  // 下单数量
  private BigDecimal number;
  // 成交金额
  private BigDecimal matchedMoney;
  private BigDecimal moneyOver;
  private BigDecimal money;
  //收取手续费币种
  private String feeAsset;
  //券商用户扣除手续费
  private BigDecimal fee;
  // 交易状态
  private OrderState orderState;

}
