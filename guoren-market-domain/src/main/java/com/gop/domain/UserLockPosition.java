package com.gop.domain;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;
@Data
public class UserLockPosition {
    private Integer id;

    private Integer uid;

    private String assetCode;

    private BigDecimal amount;

    private Date createDate;

    private Date updateDate;

}