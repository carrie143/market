package com.gop.user.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gop.code.consts.CommonCodeConst;
import com.gop.code.consts.RoleCodeConst;
import com.gop.domain.AdminRole;
import com.gop.domain.Menu;
import com.gop.domain.MenuRole;
import com.gop.domain.Role;
import com.gop.domain.enums.RoleStatus;
import com.gop.exception.AppException;
import com.gop.mapper.AdminRoleMapper;
import com.gop.mapper.MenuInterfaceMapper;
import com.gop.mapper.MenuRoleMapper;
import com.gop.mapper.MeunMapper;
import com.gop.mapper.RoleMapper;
import com.gop.user.service.RoleManagerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by wuyanjie on 2018/4/16.
 */

@Component
@Slf4j
public class RoleManagerServceImpl implements RoleManagerService {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private AdminRoleMapper adminRoleMapper;
    @Autowired
    private MenuRoleMapper menuRoleMapper;
    @Autowired
    private MeunMapper meunMapper;
    @Autowired
    private MenuInterfaceMapper menuInterfaceMapper;

    @Override
    public PageInfo<Role> getRoleList(int roleStatus,int pageNo, int pageSize) {
        PageHelper.startPage(pageNo, pageSize, true);
        PageHelper.orderBy("create_date desc");
        return new PageInfo<>(roleMapper.getRoleList(roleStatus));
    }

    @Override
    public Role getRoleById(Integer roleId) {
        Role role = new Role();
        role.setRoleId(roleId);
        role.setStatus(RoleStatus.ENABLE.getStatus());
        return roleMapper.getRoleById(role);
    }

    @Override
    public int addMenuRole(Integer roleId, Integer menuId,Integer status) {
        MenuRole menuRole = new MenuRole();
        menuRole.setRoleId(roleId);
        menuRole.setMenuId(menuId);
        menuRole.setStatus(status);
        menuRole.setCreateDate(new Date());
        menuRole.setUpdateDate(new Date());
        try {
            return menuRoleMapper.addMenuRole(menuRole);
        } catch (Exception e1) {
            log.error("插入menu_role表异常", e1);
            throw new AppException(RoleCodeConst.ROLE_MENU_ADD_ERROR);
        }
    }

    @Override
    public int updateMenuRole(Integer roleId, Integer menuId, Integer status) {
        MenuRole menuRole = new MenuRole();
        menuRole.setRoleId(roleId);
        menuRole.setMenuId(menuId);
        menuRole.setStatus(status);
        menuRole.setUpdateDate(new Date());
        try {
            return menuRoleMapper.updateMenuRole(menuRole);
        } catch (Exception e1) {
            log.error("更新menu_role表异常", e1);
            throw new AppException(RoleCodeConst.ROLE_MENU_UPDATE_ERROR);
        }
    }

    @Override
    public List<Menu> getMenuByParentId(Integer level, Integer parentId) {
        Menu menu = new Menu();
        menu.setLevel(level);
        menu.setParentId(parentId);
        return meunMapper.getMenuByParentId(menu);
    }

    @Override
    @Transactional
    public void deleteRole(Integer roleId) {
        try {
//            //删除角色和人员的关联关系
//            adminRoleMapper.deleteAdminRole(roleId);
//            //删除菜单和角色的关联
//            menuRoleMapper.deleteMenuRoleByRoleId(roleId);
            //删除角色
            Role role = new Role();
            role.setRoleId(roleId);
            role.setStatus(RoleStatus.DISABLE.getStatus());
            roleMapper.updateRoleStatus(role);
        }catch (Exception e1) {
            log.error("删除表异常", e1);
            throw new AppException(RoleCodeConst.ROLE_DELETE_ERROR);
        }
    }

    @Override
    public int createAdminRole(Integer adminId, Integer roleId) {
        int result = 0;
        AdminRole adminRole = new AdminRole();
        adminRole.setRoleId(roleId);
        adminRole.setAdminId(adminId);
        adminRole.setCreateDate(new Date());
        adminRole.setUpdateDate(new Date());
        try {
            adminRoleMapper.insert(adminRole);
        }catch (Exception e1) {
            log.error("插入admin_role表异常", e1);
            throw new AppException(RoleCodeConst.ROLE_ADMIN_CREATE_ERROR);
        }
        return result;
    }

    @Override
    public int getRoleByName(String roleName) {
        Role role = new Role();
        role.setRoleName(roleName);
        role.setStatus(RoleStatus.ENABLE.getStatus());
        return roleMapper.getRoleByName(role);
    }

    @Override
    public List<Menu> getMenuByRoleId(Integer roleId) {
    return meunMapper.getMenuByRoleId(roleId);
  }

    @Override
    public Integer getAdminRole(Integer adminId) {
        return adminRoleMapper.getAdminRole(adminId);
    }

    @Override
    public Integer checkRoleRights(Integer roleId, String uri) {
        return menuInterfaceMapper.checkInterface(roleId,uri);
    }

    @Override
    public int getMenuRole(Integer roleId, Integer menuId) {
        MenuRole menuRole = new MenuRole();
        menuRole.setRoleId(roleId);
        menuRole.setMenuId(menuId);
        return menuRoleMapper.getMenuRole(menuRole);
    }

    @Override
    public PageInfo<AdminRole> getAdminRoleList(Integer roleId, int pageNo, int pageSize) {
        PageHelper.startPage(pageNo, pageSize, true);
        PageHelper.orderBy("id desc");
        return new PageInfo<>(adminRoleMapper.getAdminRoleList(roleId));
    }

    @Override
    public List<AdminRole> getAdminRoleByRoleId( Integer roleId) {
        return adminRoleMapper.getAdminRoleList(roleId);
    }

    @Override
    public List<MenuRole> getMenuRoleList(Integer roleId,Integer status) {
        MenuRole menuRole = new MenuRole();
        menuRole.setRoleId(roleId);
        menuRole.setStatus(status);
        return menuRoleMapper.getMenuRoleList(menuRole);
    }

    @Override
    public int createRole(String roleName) {
        Role role = new Role();
        role.setRoleName(roleName);
        role.setStatus(RoleStatus.ENABLE.getStatus());
        role.setCreateDate(new Date());
        role.setUpdateDate(new Date());
        int result = 0;
        try {
            result = roleMapper.createRole(role);
        } catch (Exception e1) {
            log.error("插入role表异常", e1);
            throw new AppException(RoleCodeConst.ROLE_CREATE_ERROR);
        }
        return result;
    }
}
