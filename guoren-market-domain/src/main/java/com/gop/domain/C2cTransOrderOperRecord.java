package com.gop.domain;

import java.util.Date;

import lombok.Data;
@Data
public class C2cTransOrderOperRecord {
    private Integer id;

    private String transOrderId;

    private Integer buyUid;

    private Integer sellUid;

    private String operMsg;

    private Integer operUid;

    private String beginStatus;

    private String endStatus;

    private Date createDate;

    
}