package com.gop.api.cloud.request;

import com.gop.domain.enums.DepositCoinAssetStatus;
import java.util.Date;
import lombok.Data;

/**
 * Created by wuyanjie on 2018/6/26.
 */
@Data
public class DepositQueryRequest {
    private Long uid;
    private String assetCode;
    private String address;
    private DepositCoinAssetStatus status;
    private String startDate;
    private String endDate;
    private Integer pageNo;
    private Integer pageSize;
}
