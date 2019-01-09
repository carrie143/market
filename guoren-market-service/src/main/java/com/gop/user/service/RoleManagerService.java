package com.gop.user.service;

import com.github.pagehelper.PageInfo;
import com.gop.domain.AdminRole;
import com.gop.domain.Menu;
import com.gop.domain.MenuRole;
import com.gop.domain.Role;

import java.util.List;

/**
 * Created by wuyanjie on 2018/4/16.
 */
public interface RoleManagerService {

    PageInfo<Role> getRoleList(int roleStatus,int pageNo,int pageSize);

    Role getRoleById(Integer roleId);

    int createRole(String roleName);

    PageInfo<AdminRole> getAdminRoleList(Integer roleId ,int pageNo,int pageSize);

    List<MenuRole> getMenuRoleList(Integer roleId,Integer status);

    int addMenuRole(Integer roleId,Integer menuId,Integer status);

    int updateMenuRole(Integer roleId,Integer menuId,Integer status);

    List<Menu> getMenuByParentId(Integer level, Integer parentId);

    void deleteRole(Integer roleId);

    int createAdminRole(Integer adminId,Integer roleId);

    int getRoleByName(String roleName);

    List<Menu> getMenuByRoleId(Integer roleId);

    Integer getAdminRole(Integer adminId);

    Integer checkRoleRights(Integer roleId, String uri);

    int getMenuRole(Integer roleId,Integer menuId);

    List<AdminRole> getAdminRoleByRoleId(Integer roleId);
}
