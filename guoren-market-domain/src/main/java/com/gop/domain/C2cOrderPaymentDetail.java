package com.gop.domain;

import java.util.Date;

import com.gop.domain.enums.C2cPayType;

import lombok.Data;

@Data
public class C2cOrderPaymentDetail {
    private Integer id;

    private Integer uid;

    private String advertId;

    private C2cPayType payType;
    
    private Integer payChannelId;
    
    private Date createDate;
   
}