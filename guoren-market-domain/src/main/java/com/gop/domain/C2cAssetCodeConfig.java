package com.gop.domain;

import java.util.Date;

import com.gop.domain.enums.C2cCirculateStatus;
import com.gop.domain.enums.C2cTransType;

import lombok.Data;
@Data
public class C2cAssetCodeConfig {
    private Integer id;

    private String assetCode;

    private C2cCirculateStatus status;

    private C2cTransType type;

    private Date createDate;

    private Date updateDate;

   
}