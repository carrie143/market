package com.gop.mapper;

import com.gop.domain.Role;
import com.gop.domain.User;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by wuyanjie on 2018/4/16.
 */
public interface RoleMapper {
    List<Role> getRoleList(Integer roleStatus);

    int createRole(Role role);

    Role getRoleById(Role role);

    int deleteRole(Integer roleId);

    int updateRoleStatus(Role role);

    int getRoleByName(Role role);
}
