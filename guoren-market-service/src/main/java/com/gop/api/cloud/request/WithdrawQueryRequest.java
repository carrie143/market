package com.gop.api.cloud.request;

import com.gop.domain.enums.WithdrawCoinOrderStatus;

import java.util.Date;
import lombok.Data;

/**
 * Created by wuyanjie on 2018/6/26.
 */
@Data
public class WithdrawQueryRequest {
    private Long uid;
    private String assetCode;
    private String address;
    private WithdrawCoinOrderStatus status;
    private String startDate;
    private String endDate;
    private Integer pageNo;
    private Integer pageSize;
    private String email;
    private String clientOrderNo;
}
