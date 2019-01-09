package com.gop.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.gop.domain.enums.InviteActivityRewardConfigInviteType;

import lombok.Data;
@Data
public class InviteUserRewardRecord {
    private Integer id;

    private Integer uid;

    private Integer activityId;
    
    private InviteActivityRewardConfigInviteType inviteType;
    
    private String rewardAssetCode;

    private BigDecimal rewardAmount;

    private String requestNo;

    private Date createDate;

    private Date updateDate;

}