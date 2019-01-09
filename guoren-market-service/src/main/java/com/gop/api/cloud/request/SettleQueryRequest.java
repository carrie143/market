package com.gop.api.cloud.request;

import com.gop.api.cloud.enums.SettleType;

import lombok.Data;

/**
 * Created by wuyanjie on 2018/6/28.
 */
@Data
public class SettleQueryRequest {
    private SettleType type;
    private String assetCode;
    private Integer pageNo;
    private Integer pageSize;
}
