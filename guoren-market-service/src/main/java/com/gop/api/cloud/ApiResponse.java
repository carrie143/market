package com.gop.api.cloud;

import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import lombok.Data;

/**
 * Created by Lxa on 2018/6/6.
 *
 * @author lixianan
 */
@Data
@NoArgsConstructor
public class ApiResponse<T> {

  private boolean ret;
  private String code;
  private String msg;
  private T data;

  private ApiResponse(boolean ret, String code, String msg, T data) {
    this.ret = ret;
    this.code = code;
    this.msg = msg;
    this.data = data;
  }

  public ApiResponse(T data) {
    this(true, StringUtils.EMPTY, StringUtils.EMPTY, data);
  }

  ApiResponse(String errorCode, String errorMsg) {
   this(false, errorCode, errorMsg, null);
  }

}
