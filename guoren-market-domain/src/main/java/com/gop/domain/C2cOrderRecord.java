package com.gop.domain;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;
@Data
public class C2cOrderRecord {
    private Integer id;

    private Integer uid;

    private String orderId;

    private String advertId;

    private String requestNo;

    private String assetCode;

    private BigDecimal lockNum;

    private BigDecimal price;

    private BigDecimal money;

    private Date createDate;

   
}