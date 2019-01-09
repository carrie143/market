package com.gop.api.cloud.response;


import com.gop.api.cloud.enums.SettleType;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

/**
 * Created by wuyanjie on 2018/6/30.
 */
@Data
public class SettleRecordDto {
    private String brokerId;
    private String assetCode;
    private String requestNo;
    private SettleType type;
    private BigDecimal amountAvailable;
    private Date createDate;
}
