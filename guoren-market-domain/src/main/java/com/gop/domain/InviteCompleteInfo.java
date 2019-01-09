package com.gop.domain;

import java.util.Date;

import lombok.Data;
@Data
public class InviteCompleteInfo {
    private Integer id;

    private Integer uid;

    private Integer inviteUid;

    private String inviteEmail;

    private Integer activityId;

    private Date createDate;

    private Date updateDate;

}