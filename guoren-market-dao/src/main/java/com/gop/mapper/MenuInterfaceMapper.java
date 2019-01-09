package com.gop.mapper;

import org.apache.ibatis.annotations.Param;

/**
 * Created by YAO on 2018/4/17.
 */
public interface MenuInterfaceMapper {
  public Integer checkInterface(@Param("roleId") Integer roleId, @Param("uri")String uri);
}
