package com.gop.api.cloud.response;

import com.gop.api.cloud.common.OrderState;
import com.gop.api.cloud.common.OrderType;
import com.gop.api.cloud.common.TradeType;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

/**
 * Created by wuyanjie on 2018/4/26.
 */
@Data
public class TradeOrderDto {
    //@ApiModelProperty("挂单时间")
    Date createDate;
    //@ApiModelProperty("成交时间")
    Date finishDate;
    //@ApiModelProperty("交易对")
    String symbol;
    //@ApiModelProperty("下单类型")
    TradeType tradeType;
    //@ApiModelProperty("下单种类")
    OrderType tradeFlag;
    //@ApiModelProperty("下单流水号")
    String requestNo;
    //@ApiModelProperty("用户id")
    Long brokerUid;
    String brokerId;
    //@ApiModelProperty("下单数量")
    BigDecimal number;
    //@ApiModelProperty("下单价格")
    BigDecimal price;
    //@ApiModelProperty("已撮合数量")
    BigDecimal tradedNumber;
    //@ApiModelProperty("剩余数量")
    BigDecimal numberOver;
    //@ApiModelProperty("已撮合金额")
    BigDecimal tradedMoney;
    //@ApiModelProperty("交易手续费")
    BigDecimal fee;
    //@ApiModelProperty("券商收益手续费")
    BigDecimal brokerFee;
    //@ApiModelProperty("订单状态")
    OrderState status;

}
