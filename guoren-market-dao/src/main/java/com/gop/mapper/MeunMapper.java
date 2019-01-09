package com.gop.mapper;

import com.gop.domain.Menu;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by wuyanjie on 2018/4/16.
 */
public interface MeunMapper {
    Menu getMenuById(Integer menuId);
    List<Menu> getMenuByParentId(Menu menu);
    List<Menu> getMenuByRoleId(@Param("roleId") Integer roleId);
}
