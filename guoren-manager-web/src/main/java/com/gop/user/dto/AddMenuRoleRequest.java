package com.gop.user.dto;

import java.util.List;

import lombok.Data;

/**
 * Created by wuyanjie on 2018/6/27.
 */
@Data
public class AddMenuRoleRequest {
    private Integer roleId;
    private List<MenuAddRoleDto> menuAddRoleDtos;
}
