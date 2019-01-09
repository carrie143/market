package com.gop.user.dto;

import com.gop.domain.Menu;
import lombok.Data;

/**
 * Created by wuyanjie on 2018/4/16.
 */
@Data
public class MenuDto {
    Integer menuId;
    String menu_name;
    String menu_module;
    Integer parent_id;
    Integer level;
    String uri;
    public MenuDto(Menu menu){
        this.menuId = menu.getMenuId();
        this.menu_name = menu.getMenuName();
        this.menu_module = menu.getMenuModule();
        this.parent_id = menu.getParentId();
        this.level = menu.getLevel();
        this.uri = menu.getUri();
    }
}
