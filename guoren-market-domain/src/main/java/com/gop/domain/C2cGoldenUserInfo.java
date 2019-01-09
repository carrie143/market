package com.gop.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.gop.domain.enums.C2cCirculateStatus;

import lombok.Data;
@Data
public class C2cGoldenUserInfo {
    private Integer id;

    private Integer uid;

    private String infoId;

    private String assetCode;

    private String toAssetCode;

    private BigDecimal price;

    private C2cCirculateStatus flag;

    private Date createDate;

    private Date updateDate;

    
}