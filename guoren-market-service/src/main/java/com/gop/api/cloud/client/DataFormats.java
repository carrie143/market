package com.gop.api.cloud.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * Created by zhangjinyang on 2018/6/20.
 */

@Getter
@ToString
@AllArgsConstructor
public enum DataFormats {

  HEX(0, "text/plain"),
  JSON(1, "application/json"),
  PLAIN_TEXT(2, "text/plain");

  private final int code;
  private final String mediaType;
}

