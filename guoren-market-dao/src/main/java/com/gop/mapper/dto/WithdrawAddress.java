package com.gop.mapper.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.Data;
import lombok.ToString;

/**
 * Created by YAO on 2018/6/30.
 */
@Data
@ToString
public class WithdrawAddress {
  private Integer id;
  private String assetCode;
  private String address;
  private Timestamp createDate;
  private Timestamp updateDate;
}
