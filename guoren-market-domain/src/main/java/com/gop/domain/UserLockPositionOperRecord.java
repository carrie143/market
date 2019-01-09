package com.gop.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.gop.domain.enums.UserLockPositionStatus;

import lombok.Data;
@Data
public class UserLockPositionOperRecord {
    private Integer id;

    private Integer uid;

    private String assetCode;

    private BigDecimal amount;

    private UserLockPositionStatus status;

    private Date createDate;

    private Date updateDate;

}