package com.gop.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.gop.domain.enums.InviteActivityRewardConfigInviteType;
import com.gop.domain.enums.InviteActivityRewardConfigStatus;

import lombok.Data;
@Data
public class InviteActivityRewardConfig {
    private Integer id;

    private Integer activityId;

    private String rewardAssetCode;

    private BigDecimal amount;

    private InviteActivityRewardConfigInviteType inviteType;

    private InviteActivityRewardConfigStatus status;

    private Date createDate;

    private Date updateDate;

}