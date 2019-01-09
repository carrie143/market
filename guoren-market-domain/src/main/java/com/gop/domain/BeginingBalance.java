package com.gop.domain;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by wuyanjie on 2018/4/28.
 */
@Data
@ToString
public class BeginingBalance {
    private Integer id;
    private String assetCode;
    private BigDecimal amount_available;
    private BigDecimal amount_lock;
    private BigDecimal amount_total;
    private Date createDate;
    private Date updateDate;
}
