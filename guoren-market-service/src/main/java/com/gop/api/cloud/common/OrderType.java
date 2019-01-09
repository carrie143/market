package com.gop.api.cloud.common;

import com.gop.exception.AppException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum OrderType {
    BUY, SELL;
    //新增代码
    private static final Map<String, OrderType> stringToEnum = new HashMap<>();

    static {
      for (OrderType rs : values()) {
        stringToEnum.put(rs.name(), rs);
      }
    }

    public static OrderType getOrNull(String name) {
      return Optional.ofNullable(stringToEnum.get(name)).orElse(null);
    }

    public static OrderType get(String name) {
      return Optional.ofNullable(stringToEnum.get(name))
          .orElseThrow((() -> new AppException("不存在的订单类型类型：" + name)));
    }

  }