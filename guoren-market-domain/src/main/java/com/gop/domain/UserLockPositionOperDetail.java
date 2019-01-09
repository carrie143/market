package com.gop.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.gop.domain.enums.UserLockPositionStatus;

import lombok.Data;
@Data
public class UserLockPositionOperDetail {
    private Integer id;

    private Integer operId;

    private Integer uid;

    private String assetCode;

    private BigDecimal totalLockAmount;
    
    private BigDecimal lockAmount;

    private UserLockPositionStatus status;

    private String requestNo;

    private Date createDate;
    
}