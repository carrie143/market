package com.clouddax.java.functiontest;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created by zhangjinyang on 2018/6/27.
 */
@AllArgsConstructor
@Data
@ToString
@NoArgsConstructor
public class User {

  private String id;
  private String name;
//  private List<UserRequest> userRequests;

}
