package com.gop.api.cloud.request;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * Created by wuyanjie on 2018/6/27.
 */
@Data
public class BrokerWithdrawRequest {
    private String clientOrderNo;
    private BigDecimal amount;
    private String address;
    private String assetCode;
    private String message;
}