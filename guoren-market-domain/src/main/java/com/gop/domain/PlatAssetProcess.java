package com.gop.domain;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

/**
 * Created by wuyanjie on 2018/4/27.
 */
@Data
public class PlatAssetProcess {

    private Integer id;
    private String assetCode;
    private BigDecimal beginBalance;   //期初余额
    private BigDecimal depositBalance;  //存入金额
    private BigDecimal withdrawTotal;   //提现-申请（未确认+成功+失败）
    private BigDecimal withdrawUnknow;  //提现-未确认
    private BigDecimal withdrawRefuse;   //提现-失败
    private BigDecimal withdrawSuccess;  //提现-成功
    private BigDecimal withdrawFee;       //提现-手续费
    private BigDecimal brokenAssetBalance;  //管理员调整资产
    private BigDecimal tradeFee;       //交易手续费
    private BigDecimal other;
    private BigDecimal endBalance;     //期末余额
    private BigDecimal culBalance;    //计算余额  存入金额+存入金额-提现-成功-提现-手续费+管理员调整资产-交易手续费+其他
    private Integer status;
    private Date createDate;
    private Date updateDate;
}