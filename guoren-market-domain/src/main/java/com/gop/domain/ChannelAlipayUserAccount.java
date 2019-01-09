package com.gop.domain;

import java.util.Date;

import com.gop.domain.enums.DelFlag;

import lombok.Data;

@Data
public class ChannelAlipayUserAccount {
    private Integer id;

    private Integer uid;

    private String accountNo;

    private String accountName;
    
    private DelFlag delFlag;

    private Date createDate;

    private Date updateDate;

}