package com.gop.domain;

import java.util.Date;

import com.gop.domain.enums.SwitchStatus;

import lombok.Data;

@Data
public class ChannelOkpayUserRules {
    private Integer id;

    private String accountNo;

    private String accountName;

    private String accountCode;

    private String rules;

    private SwitchStatus status;

    private String remark;

    private Integer createUser;

    private Date createDate;

    private Integer updateUser;

    private Date updateDate;

}