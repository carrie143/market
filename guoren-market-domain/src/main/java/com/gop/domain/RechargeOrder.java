package com.gop.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class RechargeOrder implements Serializable {

    private Integer id;
    private Long payMoney;
    private Long fee;
    private Integer userId;
    private String asset;//资产"USC"
    private String payChannel;// "SZF"
    private Integer payType;//0 移动,1 电信，2联通
    private Date createTime;
    private Integer status;//1 创建,2 处理中,3 成功 4 失败
    private BigDecimal assetAmount;
    private String extraInfo;
    private String requestNo;//对应资产变更里面的No

}
