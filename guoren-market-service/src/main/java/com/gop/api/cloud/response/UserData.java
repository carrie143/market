package com.gop.api.cloud.response;

import lombok.Data;

/**
 * Created by Lxa on 2018/6/15.
 *
 * @author lixianan
 */
@Data
public class UserData {
  private String brokerId;
  private Long uid;
  private String platId;
  private String email;
  private String name;
  private String phone;
  private String gender;
  private String birthday;
  private String country;
  private Boolean isWhiteList;  //是否白名单用户
  private String ip;

}
