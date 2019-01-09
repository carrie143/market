package com.gop.domain.response;

import com.alibaba.fastjson.JSONArray;

import java.util.List;

import lombok.Data;
import lombok.ToString;

/**
 * Created by YAO on 2018/5/21.
 */
@Data
@ToString
public class WalletResponseBase {
    private Integer repCode;
  private String message;
  private JSONArray dataList;
}
