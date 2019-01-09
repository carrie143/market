package com.gop.user.dto;

import com.gop.domain.Menu;
import com.gop.domain.MenuRole;
import lombok.Data;

import java.util.List;

/**
 * Created by wuyanjie on 2018/4/16.
 */
@Data
public class MenuRoleDto {
    private Integer menuId;
    private String menuName;
    private Integer level;
    private Integer parentId;
    private String menuModule;
    private boolean hasChoose;
    private List<MenuRoleDto> menuRoleDtoList;
    public MenuRoleDto(Menu menu,List<MenuRoleDto> menuRoleDtoList, boolean hasChoose){
        this.menuId = menu.getMenuId();
        this.menuName = menu.getMenuName();
        this.level = menu.getLevel();
        this.parentId = menu.getParentId();
        this.menuModule = menu.getMenuModule();
        this.menuRoleDtoList = menuRoleDtoList;
        this.hasChoose = hasChoose;
    }
}
