package com.gop.user.dto;

import com.gop.domain.AdminRole;
import com.gop.mode.vo.BaseDto;
import lombok.Data;

import java.util.Date;

/**
 * Created by wuyanjie on 2018/4/16.
 */
@Data
public class AdminRoleDto extends BaseDto{
    Integer id;
    Integer adminId;
    String adminName;
    Integer roleId;
    String roleName;
    Date createDate;
    public AdminRoleDto(AdminRole adminRole,String adminName,String roleName){
        this.id = adminRole.getId();
        this.adminId = adminRole.getAdminId();
        this.adminName = adminName;
        this.roleId = adminRole.getRoleId();
        this.createDate = adminRole.getCreateDate();
        this.roleName = roleName;
    }
}
