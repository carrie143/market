package com.gop.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YAO on 2018/5/23.
 */
public class PageUtil<E> {
  public Integer pages;
  public long total;

  public List<E> ListPage(List<E> list, Integer pageNo, Integer pageSize) {
    int totalCount = list.size();
    total = (long) totalCount;
    pages = (int)Math.ceil(Integer.valueOf(totalCount).doubleValue()/Integer.valueOf(pageSize).doubleValue());
    if (pageSize != -1) {
      List subInfo = new ArrayList<>();
      for (int i = 0; i < pageNo; i++) {
        Integer fromIndex = i * pageSize;
        int toIndex = Math.min(totalCount, (i + 1) * pageSize);
        subInfo = list.subList(fromIndex, toIndex);
        if (toIndex == totalCount) {
          break;
        }
      }
      list = subInfo;
    }
    return list;
  }
}