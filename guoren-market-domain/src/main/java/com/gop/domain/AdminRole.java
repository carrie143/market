package com.gop.domain;

import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by wuyanjie on 2018/4/16.
 */
@Data
@ToString
public class AdminRole  implements Serializable {
    private Integer id;
    private Integer adminId;
    private Integer roleId;
    private Date createDate;
    private Date updateDate;
}
