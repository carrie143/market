package com.gop.mapper;

import com.gop.domain.AdminRole;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by wuyanjie on 2018/4/16.
 */
public interface AdminRoleMapper {
    List<AdminRole> getAdminRoleList(Integer roleId);

    void deleteAdminRole(Integer roleId);

    int insert(AdminRole adminRole);

    int getAdminRoleCount(AdminRole adminRole);
    Integer getAdminRole(@Param("adminId")Integer adminId);
}
