package com.gop.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.gop.domain.enums.SwitchStatus;

import lombok.Data;

@Data
public class ChannelOkpayGlobalConfig {
    private Integer id;

    private SwitchStatus status;

    private BigDecimal minAmount;

    private BigDecimal maxAmount;

    private Integer createUser;

    private Date createDate;

    private Integer updateUser;

    private Date updateDate;

}