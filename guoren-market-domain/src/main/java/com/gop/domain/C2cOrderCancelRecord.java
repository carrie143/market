package com.gop.domain;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;
@Data
public class C2cOrderCancelRecord {
    private Integer id;

    private Integer uid;

    private String orderId;

    private String cancelOrderId;

    private String requestNo;

    private String assetCode;

    private BigDecimal returnNum;

    private BigDecimal price;

    private BigDecimal money;

    private Date createDate;

    private Date updateDate;

   
}