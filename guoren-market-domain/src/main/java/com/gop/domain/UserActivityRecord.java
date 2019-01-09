package com.gop.domain;

import java.util.Date;

import lombok.Data;
@Data
public class UserActivityRecord {
    private Integer id;

    private Integer uid;

    private String activityType;

    private String cardType;

    private String cardNo;
    
    private String requestNo;

    private Date createDate;

   
}