package com.gop.domain;

import java.util.Date;

import lombok.Data;
import lombok.ToString;

/**
 * Created by YAO on 2018/4/24.
 */
@Data
@ToString
public class Wallet {
  private Integer id;
  private String assetCode;
  private String account;
  private String balance;
  private String type; // hot or cold
  private Date createDate;
  private Date updateDate;
}
