package com.gop.domain;

import com.gop.domain.enums.UserLockPositionRewardStatus;

import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLockPositionReward {
    private Integer id;

    private Integer uid;

    private String email;

    private String assetCode;

    private BigDecimal lockAmount;

    private BigDecimal rewardAmount;

    private String requestNo;

    private UserLockPositionRewardStatus status;
    
    private Integer rewardYear;
    
    private Integer rewardMonth;
    
    private Date createDate;

    private Date updateDate;

}