package com.gop.domain;

import java.util.Date;

import lombok.Data;
@Data
public class C2cGoldenTransOrderOperRecord {
    private Integer id;

    private String goldenTradeOrderNo;

    private Integer buyUid;

    private Integer sellUid;

    private String beginStatus;

    private String endStatus;

    private String remark;

    private Date createDate;

    private String updateDate;

   
}