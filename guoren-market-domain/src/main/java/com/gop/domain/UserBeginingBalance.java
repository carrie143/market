package com.gop.domain;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by wuyanjie on 2018/5/17.
 */
@Data
@ToString
public class UserBeginingBalance {
    private Integer id;
    private Integer uid;
    private String assetCode;
    private String accountNo;
    private BigDecimal amount_available;
    private BigDecimal amount_lock;
    private BigDecimal amount_total;
    private Integer status;
    private Date createDate;
    private Date updateDate;
}
