package com.gop.domain;

import com.gop.domain.enums.RoleStatus;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by wuyanjie on 2018/4/16.
 */
@Data
@ToString
public class Role implements Serializable{
    private Integer roleId;
    private String roleName;
    private String instruction;
    private Integer status;
    private Date createDate;
    private Date updateDate;
}
