package com.gop.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by wuyanjie on 2018/5/22.
 */
@Data
public class UserStatistics {
    private Integer id;
    private BigDecimal totalUser;
    private BigDecimal nativeUser;
    private BigDecimal advancedUser;
    private BigDecimal nativePresent;
    private BigDecimal advancedPresent;
    private Integer status;
    private Date createDate;
    private Date updateDate;
}
