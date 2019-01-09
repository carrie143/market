package com.gop.domain;

import java.util.Date;

import com.gop.domain.enums.InviteActivityConfigStatus;

import lombok.Data;
@Data
public class InviteActivityConfig {
    private Integer id;

    private String activityName;

    private InviteActivityConfigStatus status;

    private Date createDate;

    private Date updateDate;

}