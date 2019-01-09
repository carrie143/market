package com.gop.domain;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * Created by wuyanjie on 2018/4/16.
 */
@Data
@ToString
public class Menu  implements Serializable {
    private Integer menuId;
    private String menuName;
    private String menuModule;
    private Integer parentId;
    private Integer level;
    private String uri;
    private Boolean show;
    private String i18n;
}
