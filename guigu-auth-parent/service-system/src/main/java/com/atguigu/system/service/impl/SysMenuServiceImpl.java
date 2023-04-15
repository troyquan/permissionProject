package com.atguigu.system.service.impl;

import com.atguigu.common.utils.RouterHelper;
import com.atguigu.model.system.SysMenu;
import com.atguigu.model.system.SysRoleMenu;
import com.atguigu.model.vo.AssignMenuVo;
import com.atguigu.model.vo.RouterVo;
import com.atguigu.system.exception.CustomizedException;
import com.atguigu.system.mapper.SysMenuMapper;
import com.atguigu.system.mapper.SysRoleMenuMapper;
import com.atguigu.system.service.SysMenuService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.atguigu.system.service.utils.MenuHelper;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author Yongze
 * @since 2023-04-09
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    //tree menu
    @Override
    public List<SysMenu> findNodes() {
        //get all menu
        List<SysMenu> sysMenuList = baseMapper.selectList(null);
        List<SysMenu> resuleList= MenuHelper.buildTree(sysMenuList);
        return resuleList;
        //transfer menu to jason



    }

    @Override
    public void removeMenuById(String id) {
        //get present menu if has sub menu
        //id = parentId
        QueryWrapper<SysMenu> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id",id);
        Integer count = baseMapper.selectCount(wrapper);
        if (count > 0) {//it has sub menu
            throw new CustomizedException(201,"Please delete sub menu");
        }
        baseMapper.deleteById(id);
    }
    //assign menu by roles
    @Override
    public List<SysMenu> findMenuByRoleId(Long roleId) {

        //get menu where status = 1
        QueryWrapper <SysMenu> wrapperMenu = new QueryWrapper<>();
        wrapperMenu.eq("status",1);
        List<SysMenu> menuList = baseMapper.selectList(wrapperMenu);
        //get assigned list by roleId
        QueryWrapper <SysRoleMenu> wrapperRoleMenu = new QueryWrapper<>();
        wrapperRoleMenu.eq("role_id",roleId);
        List<SysRoleMenu> roleMenusAssigned = sysRoleMenuMapper.selectList(wrapperRoleMenu);
        //get assigned menuId by the list
        List<String> roleMenuIds = new ArrayList<>();
        for (SysRoleMenu sysRoleMenu:roleMenusAssigned) {
            String menuId = sysRoleMenu.getMenuId();
            roleMenuIds.add(menuId);
        }
        //if isSelect, if true else false
        //compare assigned menuId with all menu  set the value of isSelect
        for (SysMenu sysMenu:menuList) {
            if(roleMenuIds.contains(sysMenu.getId())){
                sysMenu.setSelect(true);
            }else {
                sysMenu.setSelect(false);
            }
        }
        //transfer the data to tree set by Menu Helper
        List<SysMenu> sysMenus = MenuHelper.buildTree(menuList);
        return sysMenus;
    }

    @Override
    public void doAssign(AssignMenuVo assignMenuVo) {
        //delete menu permission by id
        QueryWrapper<SysRoleMenu> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id",assignMenuVo.getRoleId());
        sysRoleMenuMapper.delete(wrapper);
        //iterate menu list, add one by one
        List<String> menuIdList = assignMenuVo.getMenuIdList();
        for (String menuId:menuIdList) {
            SysRoleMenu sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setMenuId(menuId);
            sysRoleMenu.setRoleId(assignMenuVo.getRoleId());
            sysRoleMenuMapper.insert(sysRoleMenu);
        }
    }

    @Override
    public List<RouterVo> getUserMenuList(String userId) {
        //admin is super management,
        //if id is 1 , has permission for all data
        List<SysMenu> sysMenus = null;
        if ("1".equals(userId)){
            QueryWrapper<SysMenu> wrapper = new QueryWrapper<>();
            wrapper.eq("status",1);
            wrapper.orderByAsc("sort_value");
            sysMenus = baseMapper.selectList(wrapper);
        }else{
            //else, get permissionList of the user
            sysMenus = baseMapper.findMenuListUserId(userId);
        }
        //create tree set
        List<SysMenu> sysMenuTree = MenuHelper.buildTree(sysMenus);
        //transfer front end router type
        List<RouterVo> routerVos = RouterHelper.buildRouters(sysMenuTree);
        return routerVos;
    }

    @Override
    public List<String> getUserButton(@Param("userId") String userId) {
        List<SysMenu> sysMenuList = null;
        if ("1".equals(userId)){
            sysMenuList = baseMapper.selectList(new QueryWrapper<SysMenu>().eq("status", 1));
        }else{
            sysMenuList = baseMapper.findMenuListUserId(userId);
        }
        List<String> permissionList = new ArrayList<>();
        //iterate sysMenuList get perms add  to new list
        for (SysMenu sysMenu:sysMenuList) {
            //type = 2
            if (sysMenu.getType() == 2) {
                String perms = sysMenu.getPerms();
                permissionList.add(perms);
            }
        }
        return permissionList;
    }

}
