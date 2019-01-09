package com.gop.api.cloud.request;


import com.gop.api.cloud.common.OrderState;
import com.gop.api.cloud.common.OrderType;
import com.gop.api.cloud.common.TradeType;

import lombok.Data;

/**
 * Created by wuyanjie on 2018/6/28.
 */
@Data
public class TradeOrderQueryReq {
    private Long uid;
    private TradeType tradeFlag;
    private String tradeAsset;
    private String priceAsset;
    private OrderType orderType;
    private OrderState status;
    private String beginTime;
    private String endTime;
    private Integer pageNo;
    private Integer pageSize;
}
