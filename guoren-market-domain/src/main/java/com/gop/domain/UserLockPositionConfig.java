package com.gop.domain;

import java.util.Date;

import lombok.Data;
@Data
public class UserLockPositionConfig {
    private Integer id;

    private String assetCode;

    private UserLockPositionConfigType profileKey;

    private String profileValue;

    private Date createDate;

    private Date updateDate;

}