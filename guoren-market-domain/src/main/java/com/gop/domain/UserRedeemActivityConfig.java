package com.gop.domain;

import java.util.Date;

import com.gop.domain.enums.UserRedeemActivityConfigStatus;

import lombok.Data;
@Data
public class UserRedeemActivityConfig {
    private Integer id;

    private String activityName;

    private String assetCode;

    private Date beginDate;

    private Date endDate;

    private UserRedeemActivityConfigStatus status;

    private Date createDate;

    private Date updateDate;

}