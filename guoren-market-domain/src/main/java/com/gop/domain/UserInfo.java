package com.gop.domain;

import java.util.Date;

import lombok.Data;
@Data
public class UserInfo {
    private Integer uid;

    private String userNo;

    private String name;

    private String idNumber;

    private Boolean idType;

    private Date idAuthDate;

    private String idAuthip;

    private Date mobileBindDate;
    

   
}