package com.gop.domain;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;
@Data
public class UserRedeemActivityDetail {
    private Integer id;

    private Integer uid;

    private Integer redeemId;

    private String assetCode;

    private BigDecimal amount;

    private String requestNo;

    private Date createDate;

    
}