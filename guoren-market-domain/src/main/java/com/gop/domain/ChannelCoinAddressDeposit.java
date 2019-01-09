package com.gop.domain;

import java.util.Date;

import com.gop.domain.enums.DelFlag;

import lombok.Data;

@Data
public class ChannelCoinAddressDeposit {
    private Integer id;

    private Integer uid;

    private String assetCode;

    private Integer depositPoolId;

    private String name;

    private String coinAddress;

    private DelFlag delFlag;

    private Date createDate;

    private String createIp;

}