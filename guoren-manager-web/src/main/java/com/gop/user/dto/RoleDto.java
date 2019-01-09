package com.gop.user.dto;

import com.gop.domain.Role;
import com.gop.domain.enums.RoleStatus;
import lombok.Data;

/**
 * Created by wuyanjie on 2018/4/16.
 */
@Data
public class RoleDto {
    Integer roleId;
    String roleName;
    String instruction;
    Integer status;
    int count;
    public RoleDto(Role role,int count){
        this.roleId = role.getRoleId();
        this.roleName = role.getRoleName();
        this.instruction = role.getInstruction();
        this.status = role.getStatus();
        this.count = count;
    }
}