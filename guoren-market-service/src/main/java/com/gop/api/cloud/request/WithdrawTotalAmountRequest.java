package com.gop.api.cloud.request;

import com.gop.domain.enums.WithdrawCoinOrderStatus;

import lombok.Data;

/**
 * Created by wuyanjie on 2018/6/29.
 */
@Data
public class WithdrawTotalAmountRequest {
    private Long uid;
    private String assetCode;
    private WithdrawCoinOrderStatus status;
}
