package com.clouddax.java.functiontest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by zhangjinyang on 2018/6/27.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest<T> {

  private T t;
}
