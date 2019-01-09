package com.gop.api.cloud.request;

import lombok.Data;

/**
 * Created by Lxa on 2018/6/26.
 *
 * @author lixianan
 */
@Data
public class CreateUserReq {
  private Long nanoTime;
  private Long brokerUid;
  private String email;
  private String name;
  private String phone;
  private String gender;
  private String birthday;
  private String country;
  private String ip;

}
