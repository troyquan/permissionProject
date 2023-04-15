package com.atguigu.system.controller;


import com.atguigu.common.result.Result;
import com.atguigu.model.system.SysMenu;
import com.atguigu.model.vo.AssignMenuVo;
import com.atguigu.system.service.SysMenuService;
import com.baomidou.mybatisplus.extension.api.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 菜单表 前端控制器
 * </p>
 *
 * @author Yongze
 * @since 2023-04-09
 */
@Api("Menu Management")
@RestController
@RequestMapping("/admin/system/sysMenu")
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;

    @ApiOperation("assign role permission")
    @PostMapping("doAssign")
    public Result doAssign(@RequestBody AssignMenuVo assignMenuVo){
        sysMenuService.doAssign(assignMenuVo);
        return Result.ok();

    }

    //assign menu by roles
    @ApiOperation("get menu by roles")
    @GetMapping("/toAssign/{roleId}")
    public Result toAssign(@PathVariable Long roleId){
        List<SysMenu> listMenu = sysMenuService.findMenuByRoleId(roleId);
        return Result.ok(listMenu);
    }

    //menu list
    @ApiOperation("menu list")
    @GetMapping("findNodes")
    public Result findNodes(){
        List<SysMenu> list = sysMenuService.findNodes();
        return Result.ok(list);
    }

    @ApiOperation("add menu")
    @PostMapping("save")
    public Result save(@RequestBody SysMenu sysMenu){
        sysMenuService.save(sysMenu);
        return Result.ok();
    }

    @ApiOperation("get menu by Id")
    @GetMapping("findNodes/{id}")
    public Result findNodeById(@PathVariable String id){
        SysMenu sysMenu = sysMenuService.getById(id);
        return Result.ok(sysMenu);
    }

    @ApiOperation("update menu")
    @PutMapping ("update")
    public Result update(@RequestBody SysMenu sysMenu){
        sysMenuService.updateById(sysMenu);
        return Result.ok();
    }

    @ApiOperation("delete menu")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable String id){
        sysMenuService.removeMenuById(id);
        return Result.ok();
    }
}

