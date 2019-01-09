package com.gop.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.gop.domain.enums.AssetStatus;
import com.gop.domain.enums.CoinAddressType;
import com.gop.domain.enums.CurrencyType;

import lombok.Data;

@Data
public class ConfigAsset {
    private Integer id;

    private String assetCode;

    private CurrencyType currencyType;
    
    private CoinAddressType coinAddressType;
    private AssetStatus status;

    private String name;

    private BigDecimal supplyAmount;

    private BigDecimal totalAmount;

    private Integer minPrecision;

    private String description;

    private String webUrl;

    private Date createDate;

    private Date updateDate;
}