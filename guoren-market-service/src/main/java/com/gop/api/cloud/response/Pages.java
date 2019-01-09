package com.gop.api.cloud.response;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created by Lxa on 2017/5/3.
 *
 * @author lixianan
 */
@Data
public class Pages<T> {
  private static final long serialVersionUID = 1L;
  //当前页
  private int pageNum;
  //每页的数量
  private int pageSize;
  //当前页的数量
  private int size;
  //总记录数
  private long total;
  //总页数
  private long pages;
  //limit起始索引
  private int startIndex;
  //结果集
  private List<T> list;


}