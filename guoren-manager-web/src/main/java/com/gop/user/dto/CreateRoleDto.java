package com.gop.user.dto;

import com.gop.mode.vo.BaseDto;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by wuyanjie on 2018/4/16.
 */
public class CreateRoleDto extends BaseDto{
    @NotBlank
    private String roleName;
}
