package com.gop.mapper;

import com.gop.domain.MenuRole;

import java.util.List;

/**
 * Created by wuyanjie on 2018/4/16.
 */
public interface MenuRoleMapper {
    List<MenuRole> getMenuRoleList(MenuRole menuRole);

    int addMenuRole(MenuRole menuRole);

    int getMenuRole(MenuRole menuRole);

    int updateMenuRole(MenuRole menuRole);
}
