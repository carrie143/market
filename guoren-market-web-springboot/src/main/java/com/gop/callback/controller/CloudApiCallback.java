package com.gop.callback.controller;

import com.gop.api.cloud.enums.CallbackType;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by YAO on 2018/7/10.
 */
@Data
@NoArgsConstructor
public class CloudApiCallback {

  private String code;
  private CallbackType type;
  private String data;

  private CloudApiCallback(String code, CallbackType type, String data) {
    this.code = code;
    this.type = type;
    this.data = data;
  }
}