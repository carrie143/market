package com.gop.api.cloud.response;

import com.gop.domain.enums.AssetStatus;

import java.util.Date;

import lombok.Data;

/**
 * Created by wuyanjie on 2018/6/12.
 */
@Data
public class BrokerConfigAsset {
    private Long id;

    private String brokerId;

    private String assetCode;

    private AssetStatus status;

    private String description;

    private Date createDate;

    private Date updateDate;


}
