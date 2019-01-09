package com.gop.domain;

import java.util.Date;

import com.gop.domain.enums.C2cPayAccountStatus;

import lombok.Data;
@Data
public class C2cAlipayInfo {
    private Integer id;

    private Integer uid;

    private String name;

    private String alipayNo;

    private C2cPayAccountStatus status;
    
    private Integer addIndex;
    
    private Date createDate;

    private Date updateDate;

}