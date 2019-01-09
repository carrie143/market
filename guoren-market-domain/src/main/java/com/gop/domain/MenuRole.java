package com.gop.domain;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by wuyanjie on 2018/4/16.
 */
@Data
@ToString
public class MenuRole  implements Serializable {
    private Integer id;
    private Integer menuId;
    private Integer roleId;
    private Integer status;
    private Date createDate;
    private Date updateDate;
}
