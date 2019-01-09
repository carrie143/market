package com.gop.api.cloud.response;

import com.gop.domain.enums.AssetStatus;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

/**
 * Created by wuyanjie on 2018/6/12.
 */
@Data
public class BrokerConfigAssetDto {
    private Integer id;

    private String brokerId;

    private String assetCode;

    private String name;
    private BigDecimal minWithdrawAmount;  //券商提现最小限额
    private BigDecimal brokerWithdrawFee;  //券商提现手续费
    private BigDecimal withdrawFee;

    private AssetStatus status;

    private Date createDate;

}
