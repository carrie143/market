package com.gop.domain;

import java.util.Date;

import lombok.Data;
@Data
public class InviteUserInfo {
    private Integer id;

    private Integer uid;

    private String inviteCode;

    private Date createDate;

    private Date updateDate;

}