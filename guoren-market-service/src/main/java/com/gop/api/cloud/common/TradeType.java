package com.gop.api.cloud.common;

import com.gop.exception.AppException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum TradeType {
    FIXED, MARKET;

    private static final Map<String, TradeType> stringToEnum = new HashMap<>();

    static {
      for (TradeType rs : values()) {
        stringToEnum.put(rs.name(), rs);
      }
    }

    public static TradeType getOrNull(String name) {
      return Optional.ofNullable(stringToEnum.get(name)).orElse(null);
    }

    public static TradeType get(String name) {
      return Optional.ofNullable(stringToEnum.get(name)).orElseThrow((() -> new AppException("不存在的交易类型：" + name)));
    }
  }