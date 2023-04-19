package com.atguigu.system.controller;

import com.atguigu.common.result.Result;
import com.atguigu.model.system.SysRole;
import com.atguigu.model.vo.AssignRoleVo;
import com.atguigu.model.vo.SysRoleQueryVo;
import com.atguigu.system.annotation.Log;
import com.atguigu.system.enums.BusinessType;
import com.atguigu.system.exception.CustomizedException;
import com.atguigu.system.service.SysRoleService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.PushBuilder;
import java.util.List;
import java.util.Map;

@Api("RoleManagement")
@RestController
@RequestMapping("/admin/system/sysRole")
public class SysRoleController {
    @Autowired
    public SysRoleService sysRoleService;
    //delete multiple ids[1,2,3]

    @Log(title = "Role Management", businessType = BusinessType.ASSGIN)
    @PreAuthorize("hasAuthority('bnt.sysRole.assignAuth')")
    @ApiOperation("assign user role")
    @PostMapping("doAssign")
    public Result doAssign(@RequestBody AssignRoleVo assignRoleVo) {
        sysRoleService.doAssign(assignRoleVo);
        return Result.ok();

    }

    @ApiOperation("get user Role data")
    @GetMapping("toAssign/{userId}")
    public Result toAssign(@PathVariable String userId) {
        Map<String, Object> roleMap = sysRoleService.getRoleByUserId(userId);
        return Result.ok(roleMap);
    }

    @Log(title = "Role Management", businessType = BusinessType.DELETE)
    @PreAuthorize("hasAuthority('bnt.sysRole.remove')")
    @ApiOperation("batchRemove")
    @DeleteMapping("batchRemove")
    public Result batchRemove(@RequestBody List<Long> ids) {
        sysRoleService.removeByIds(ids);
        return Result.ok();
    }

    @Log(title = "Role Management", businessType = BusinessType.UPDATE)
    @PreAuthorize("hasAuthority('bnt.sysRole.update')")
    @ApiOperation("update")
    @PostMapping("update")
    public Result updateById(@RequestBody SysRole sysRole) {
        boolean isSuccess = sysRoleService.updateById(sysRole);
        if (isSuccess) {
            return Result.ok();
        } else {
            return Result.fail();
        }

    }

    @PreAuthorize("hasAuthority('bnt.sysRole.list')")
    @ApiOperation("findById")
    @GetMapping("findById/{id}")
    public Result findById(@PathVariable String id) {
        SysRole sysRoleFound = sysRoleService.getById(id);
        return Result.ok(sysRoleFound);
    }

    @Log(title = "Role Management", businessType = BusinessType.INSERT)
    @PreAuthorize("hasAuthority('bnt.sysRole.add')")
    @ApiOperation("add")
    @PostMapping("save")
    public Result saveRole(@RequestBody SysRole sysRole) {
        boolean isSuccess = sysRoleService.save(sysRole);
        if (isSuccess) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    //pagination
    @PreAuthorize("hasAuthority('bnt.sysRole.list')")
    @ApiOperation("pagination")
    @GetMapping("{page}/{limit}")
    public Result index(
            @ApiParam(name = "page", value = "present page", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "page limit", required = true)
            @PathVariable Long limit,

            @ApiParam(name = "roleQueryVo", value = "searchObj", required = false)
            SysRoleQueryVo roleQueryVo) {
        Page<SysRole> pageParam = new Page<>(page, limit);
        IPage<SysRole> pageModel = sysRoleService.selectPage(pageParam, roleQueryVo);
        return Result.ok(pageModel);
    }
    @Log(title = "Role Management", businessType = BusinessType.DELETE)
    @PreAuthorize("hasAuthority('bnt.sysRole.remove')")
    @ApiOperation(value = "Logic Delete")
    @DeleteMapping("remove/{id}")
    public Result removeRole(@PathVariable String id) {
        //call the function to delete
        boolean isSuccess = sysRoleService.removeById(id);
        if (isSuccess) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    @ApiOperation(value = "find all")
    @GetMapping("findAll")
    public Result findAllRole() {


        List<SysRole> list = sysRoleService.list();
        return Result.ok(list);
    }
}
