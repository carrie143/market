package com.gop.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.gop.domain.enums.C2cBuyAdvertStatus;

import lombok.Data;
@Data
public class C2cBuyAdvertisement {
    private Integer id;

    private String advertId;

    private Integer uid;

    private String nickname;

    private String assetCode;

    private String country;

    private String currency;

    private BigDecimal tradePrice;

    private BigDecimal buyPrice;

    private C2cBuyAdvertStatus status;

    private String remark;

    private Date createDate;

    private Date updateDate;

    
}