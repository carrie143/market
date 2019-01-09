package com.gop.api.cloud.response;

import java.math.BigDecimal;

import lombok.Data;

/**
 * Created by wuyanjie on 2018/6/29.
 */
@Data
public class BrokerAssetDto {
    private String brokerId;

    private String accountNo;

    private String assetCode;

    private BigDecimal amountAvailable;

    private BigDecimal amountLock;
}
