package com.gop.api.cloud.common;

import com.gop.exception.AppException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum OrderState {
    WAITING, PROCESSING, SUCCESS, CANCEL, FAIL;

    private static final Map<String, OrderState> stringToEnum = new HashMap<>();

    static {
      for (OrderState rs : values()) {
        stringToEnum.put(rs.name(), rs);
      }
    }

    public static OrderState getOrNull(String name) {
      return Optional.ofNullable(stringToEnum.get(name)).orElse(null);
    }

    public static OrderState get(String name) {
      return Optional.ofNullable(stringToEnum.get(name)).orElseThrow((() -> new AppException("不存在的订单状态：" + name)));
    }
  }