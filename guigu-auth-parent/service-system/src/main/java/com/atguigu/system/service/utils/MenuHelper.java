package com.atguigu.system.service.utils;

import com.atguigu.model.system.SysMenu;

import java.util.ArrayList;
import java.util.List;

public class MenuHelper {
    //create tree structure
    public static List<SysMenu> buildTree(List<SysMenu> sysMenuList) {
        //crate list for final data
        List<SysMenu> trees = new ArrayList<>();
        //iterate sysMenuList
        for (SysMenu sysMenu:sysMenuList) {
            //find parentId = 0
            if(sysMenu.getParentId().longValue() == 0){
                trees.add(findChildren(sysMenu,sysMenuList));
            }
        }
        return trees;
    }

    //recursion for children
    // if id = parentId is the same, its children
    private static SysMenu findChildren(SysMenu sysMenu, List<SysMenu> sysMenuList) {
        //data initilize
        sysMenu.setChildren(new ArrayList<>());
        //iterate to find
        for (SysMenu presentMenu :sysMenuList) {
            //get present menu id
//            String idPresent = sysMenu.getId();
//            long idPresentLong = Long.parseLong(idPresent);
//            //get all menu parentId
//            Long parentId = presentMenu.getParentId();
            if (Long.parseLong(sysMenu.getId()) == presentMenu.getParentId()) {
                if (sysMenu.getChildren() == null) {
                    sysMenu.setChildren(new ArrayList<>());
                }
                sysMenu.getChildren().add(findChildren(presentMenu,sysMenuList));
            }
        }
        return sysMenu;
    }
}
