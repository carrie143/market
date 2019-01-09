package com.gop.user.controller;

import com.github.pagehelper.PageInfo;
import com.gop.code.consts.CommonCodeConst;
import com.gop.domain.*;
import com.gop.domain.enums.MenuLevel;
import com.gop.domain.enums.RoleStatus;
import com.gop.user.dto.AddMenuRoleRequest;
import com.gop.exception.AppException;
import com.gop.mode.vo.PageModel;
import com.gop.user.dto.*;
import com.gop.user.service.AdministractorService;
import com.gop.user.service.RoleManagerService;
import com.gop.util.TokenHelper;
import com.gop.web.base.annotation.AuthForHeader;
import com.gop.web.base.auth.AuthContext;
import com.gop.web.base.auth.annotation.Strategy;
import com.gop.web.base.auth.annotation.Strategys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by wuyanjie on 2018/4/16.
 */
@RestController("RoleManagerController")
@RequestMapping("/roleManager")
@Slf4j
public class RoleManagerController {
    @Autowired
    private RoleManagerService roleManagerService;

    @Autowired
    private AdministractorService administractorService;

    @Autowired
    private TokenHelper tokenHelper;

    @Strategys(strategys = { @Strategy(authStrategy = "exe({{'CheckUserRoleStrategy'}})"),
        @Strategy(authStrategy = "exe({{'checkLoginStrategy'}})") })
    @RequestMapping(value = "/role-list", method = RequestMethod.GET)
    // @ApiOperation("角色列表")
    public PageModel<RoleDto> roleList(@AuthForHeader AuthContext context, @RequestParam("pageSize") Integer pageSize, @RequestParam("pageNo") Integer pageNo) {

        PageInfo<Role> pageInfo = roleManagerService.getRoleList(RoleStatus.ENABLE.getStatus(),pageNo, pageSize);
        PageModel<RoleDto> pageMode = new PageModel<>();
        pageMode.setPageNo(pageNo);
        pageMode.setPageSize(pageSize);
        pageMode.setPageNum(pageInfo.getPages());
        pageMode.setTotal(pageInfo.getTotal());

        List<RoleDto> lstDtos = pageInfo.getList().stream().map(role -> new RoleDto(role,
                roleManagerService.getAdminRoleByRoleId(role.getRoleId()).size()
                )).collect(Collectors.toList());
        pageMode.setList(lstDtos);
        return pageMode;
    }

    @Strategys(strategys = { @Strategy(authStrategy = "exe({{'CheckUserRoleStrategy'}})"),
        @Strategy(authStrategy = "exe({{'checkLoginStrategy'}})"),
            @Strategy(authStrategy = "exe({{'checkLoginPasswordStrategy'}})") })
    @RequestMapping(value = "/add-role", method = RequestMethod.GET)
    // @ApiOperation("添加角色")
    public void addRole(@AuthForHeader AuthContext context, @RequestParam("roleName") String roleName) {
        if (roleName == null || "".equals(roleName)) {
            throw new AppException(CommonCodeConst.FIELD_ERROR);
        }
        log.info("roleName is : " + roleName);
        int count = roleManagerService.getRoleByName(roleName);
        if(count > 0){
            throw new AppException(CommonCodeConst.ROLE_EXIST);
        }
        roleManagerService.createRole(roleName);
    }

    @Strategys(strategys = { @Strategy(authStrategy = "exe({{'CheckUserRoleStrategy'}})"),
        @Strategy(authStrategy = "exe({{'checkLoginStrategy'}})") })
    @RequestMapping(value = "/admin-role-list", method = RequestMethod.GET)
    // @ApiOperation("人员角色列表")
    public PageModel<AdminRoleDto> getAdminRoleList(@AuthForHeader AuthContext context, @RequestParam("roleId") Integer roleId,
                                                    @RequestParam("pageSize") Integer pageSize, @RequestParam("pageNo") Integer pageNo) {
        if (roleId == 0) {
            throw new AppException(CommonCodeConst.FIELD_ERROR);
        }
        log.info("roleId is : " + roleId);
        PageInfo<AdminRole> pageInfo = roleManagerService.getAdminRoleList(roleId,pageNo,pageSize);
        PageModel<AdminRoleDto> pageMode = new PageModel<>();
        pageMode.setPageNo(pageNo);
        pageMode.setPageSize(pageSize);
        pageMode.setPageNum(pageInfo.getPages());
        pageMode.setTotal(pageInfo.getTotal());

        List<AdminRoleDto> lstDtos = pageInfo.getList().stream().map(adminRole -> new AdminRoleDto(
                adminRole,
                administractorService.getAdministractor(adminRole.getAdminId()).getOpName(),
                roleManagerService.getRoleById(adminRole.getRoleId()).getRoleName()
        )).collect(Collectors.toList());

        pageMode.setList(lstDtos);
        return pageMode;
    }

    @Strategys(strategys = { @Strategy(authStrategy = "exe({{'CheckUserRoleStrategy'}})"),
        @Strategy(authStrategy = "exe({{'checkLoginStrategy'}})")})
    @RequestMapping(value = "/menu-role-list", method = RequestMethod.GET)
    // @ApiOperation("角色菜单列表")
    public List<MenuRoleDto> getMenuRoleList(@AuthForHeader AuthContext context, @RequestParam("roleId") Integer roleId) {
        if (roleId == null) {
            throw new AppException(CommonCodeConst.FIELD_ERROR);
        }
        List<Menu> pageInfo = roleManagerService.getMenuByParentId(MenuLevel.LEVEL0.getLevel(),Integer.parseInt("0"));

        List<MenuRole> menuRoles = roleManagerService.getMenuRoleList(roleId,RoleStatus.ENABLE.getStatus());
        List<MenuRoleDto> lstDtos = pageInfo.stream().map(menu -> new MenuRoleDto(
                menu,
                roleManagerService.getMenuByParentId(MenuLevel.LEVEL1.getLevel(),menu.getMenuId())
                        .stream().map(childMenu -> new MenuRoleDto(
                                childMenu,
                                null,
                                (menuRoles == null||menuRoles.size()==0)?false:isChoose (menuRoles,childMenu.getMenuId())
                )).collect(Collectors.toList()),

                (menuRoles == null||menuRoles.size()==0)?false:isChoose (menuRoles,menu.getMenuId())
        )).collect(Collectors.toList());

        return lstDtos;
    }

    private boolean isChoose(List<MenuRole> menuRoles,Integer menuId)
    {
        for(MenuRole menuRole : menuRoles){
            if( menuRole.getMenuId().equals(menuId)) {
                return true;
            }
        }
        return false;
    }

    @Strategys(strategys = { @Strategy(authStrategy = "exe({{'CheckUserRoleStrategy'}})"),
                             @Strategy(authStrategy = "exe({{'checkLoginPasswordStrategy'}})"),
        @Strategy(authStrategy = "exe({{'checkLoginStrategy'}})") })
    @RequestMapping(value = "/update-role-menu", method = RequestMethod.POST)
    // @ApiOperation("更新角色菜单权限")
    public void updateMenuRole(@AuthForHeader AuthContext context,@RequestBody AddMenuRoleRequest request) {
        Integer roleId = request.getRoleId();
        List<MenuAddRoleDto> menuAddRoleDtoList = request.getMenuAddRoleDtos();
        if (roleId==null || menuAddRoleDtoList == null){
            throw new AppException(CommonCodeConst.FIELD_ERROR);}

        for(MenuAddRoleDto menuAddRoleDto : menuAddRoleDtoList){
            int count = roleManagerService.getMenuRole(roleId,menuAddRoleDto.getMenuId());
            if(menuAddRoleDto.isHasChoose()){
                if(count > 0) {
                    roleManagerService.updateMenuRole(roleId, menuAddRoleDto.getMenuId(), RoleStatus.ENABLE.getStatus());
                }else{
                    roleManagerService.addMenuRole(roleId, menuAddRoleDto.getMenuId(), RoleStatus.ENABLE.getStatus());
                }
            }else{
                if(count > 0) {
                    roleManagerService.updateMenuRole(roleId, menuAddRoleDto.getMenuId(),RoleStatus.DISABLE.getStatus());
                }
            }

        }
    }

    @Strategys(strategys = { @Strategy(authStrategy = "exe({{'CheckUserRoleStrategy'}})"),
        @Strategy(authStrategy = "exe({{'checkLoginStrategy'}})"),
            @Strategy(authStrategy = "exe({{'checkLoginPasswordStrategy'}})") })
    @RequestMapping(value = "/delete_role", method = RequestMethod.GET)
    // @ApiOperation("删除角色")
    public void deleteRole(@AuthForHeader AuthContext context, @RequestParam("roleId") Integer roleId) {
        if (roleId == null ) {
            throw new AppException(CommonCodeConst.FIELD_ERROR);
        }
        List<MenuRole> menuRoleList = roleManagerService.getMenuRoleList(roleId,RoleStatus.ENABLE.getStatus());
        if(menuRoleList != null && menuRoleList.size()>0){
            throw new AppException(CommonCodeConst.ROLE_CAN_NOT_DELETE);
        }
        List<AdminRole> adminRoleList = roleManagerService.getAdminRoleByRoleId(roleId);
        if(adminRoleList != null && adminRoleList.size()>0){
            throw new AppException(CommonCodeConst.ROLE_CAN_NOT_DELETE);
        }
        roleManagerService.deleteRole(roleId);
    }

    @Strategys(strategys = { @Strategy(authStrategy = "exe({{'checkLoginStrategy'}})") })
    @RequestMapping(value = "/menu-admin-list", method = RequestMethod.GET)
    // @ApiOperation("获取用户权限菜单列表")
    public List<Menu> getMenuAdmin(@AuthForHeader AuthContext context) {
        Integer uid = tokenHelper.validToken(context.getToke());
        Integer roleId = roleManagerService.getAdminRole(uid);
        return roleManagerService.getMenuByRoleId(roleId);
    }
}
