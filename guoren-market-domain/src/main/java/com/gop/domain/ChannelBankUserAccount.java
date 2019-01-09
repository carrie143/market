package com.gop.domain;

import java.util.Date;

import com.gop.domain.enums.DelFlag;

import lombok.Data;

@Data
public class ChannelBankUserAccount {
    private Integer id;

    private Integer uid;

    private String assetCode;

    private String bank;

    private String subbank;

    private String name;

    private String acnumber;

    private String province;

    private String city;

    private Date createDate;

    private Date updateDate;

    private DelFlag delFlag;

   
}