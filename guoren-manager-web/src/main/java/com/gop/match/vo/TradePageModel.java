package com.gop.match.vo;

import java.math.BigDecimal;
import java.util.List;

import lombok.ToString;
import lombok.Data;

/**
 * Created by YAO on 2018/4/4.
 */
@Data
@ToString
public class TradePageModel<T> {
  private List<T> list;
  private BigDecimal number; //成交量
  private BigDecimal amount; //成交额
  private BigDecimal buyFee;
  private BigDecimal sellFee;
  private Integer pageNum;
  private Integer pageNo;
  private Integer pageSize;
  private Long total;



}