package com.gop.domain;

import java.util.Date;

import com.gop.domain.enums.C2cPayAccountStatus;

import lombok.Data;
@Data
public class C2cBankInfo {
    private Integer id;

    private Integer uid;

    private String bank;

    private String subbank;

    private String name;

    private String acnumber;

    private String province;
    
    private Integer addIndex;
    
    private String city;

    private C2cPayAccountStatus status;

    private Date createDate;

    private Date updateDate;

}