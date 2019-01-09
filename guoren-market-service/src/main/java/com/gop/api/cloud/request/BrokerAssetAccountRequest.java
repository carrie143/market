package com.gop.api.cloud.request;

import java.util.List;

import lombok.Data;

/**
 * Created by wuyanjie on 2018/6/13.
 */
@Data
public class BrokerAssetAccountRequest {
    private Long uid;
    private List<String> assetList;
}
