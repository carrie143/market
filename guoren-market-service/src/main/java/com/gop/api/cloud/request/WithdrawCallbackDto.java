package com.gop.api.cloud.request;

import com.alibaba.fastjson.JSONObject;
import com.gop.api.cloud.response.WithdrawCoinDetailDto;
import com.gop.domain.enums.WithdrawCoinOrderStatus;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

/**
 * Created by YAO on 2018/7/9.
 */
@Data
public class WithdrawCallbackDto {
  private String brokerId;

  private Long uid;

  private String assetCode;

  private String clientOrderNo;

  private String coinAddress;

  private BigDecimal number;

  private BigDecimal realNumber;

  private BigDecimal fee;

  private WithdrawCoinOrderStatus status;

  private String message;

  private Date createDate; //提现时间

  private Date finishDate;  //最后处理时间

  private Long nanoTime;
}
