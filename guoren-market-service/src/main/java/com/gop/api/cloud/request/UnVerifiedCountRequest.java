package com.gop.api.cloud.request;

import com.gop.domain.enums.WithdrawCoinOrderStatus;

import lombok.Data;

/**
 * Created by wuyanjie on 2018/6/28.
 */
@Data
public class UnVerifiedCountRequest {
    private Long uid;
    private String assetCode;
    private WithdrawCoinOrderStatus status;
}
