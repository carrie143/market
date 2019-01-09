package com.gop.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.gop.domain.enums.C2cSellAdvertStatus;

import lombok.Data;
@Data
public class C2cSellAdvertisement {
    private Integer id;

    private String advertId;

    private Integer uid;

    private String nickname;

    private String assetCode;

    private String country;

    private String phone;

    private String currency;

    private BigDecimal tradePrice;

    private BigDecimal minSellAmount;

    private BigDecimal maxSellAmount;

    private String remark;

    private C2cSellAdvertStatus status;

    private String requestNo;

    private BigDecimal lockNum;

    private BigDecimal fee;

    private Date createDate;

    private Date updateDate;

   
}