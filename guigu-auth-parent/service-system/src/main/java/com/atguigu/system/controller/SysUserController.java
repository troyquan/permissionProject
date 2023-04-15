package com.atguigu.system.controller;


import com.atguigu.common.result.Result;
import com.atguigu.common.utils.MD5;
import com.atguigu.model.system.SysUser;
import com.atguigu.model.vo.AssignRoleVo;
import com.atguigu.model.vo.SysUserQueryVo;
import com.atguigu.system.service.SysUserService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author Yongze
 * @since 2023-04-08
 */
@Api("user management")
@RestController
@RequestMapping("/admin/system/sysUser")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;



    @ApiOperation("update user status")
    @GetMapping("updateStatus/{id}/{status}")
    public Result updateStatus(@PathVariable String id,
                               @PathVariable Integer status){
        sysUserService.updateStatus(id,status);
        return Result.ok();
    }

    @ApiOperation("User List")
    @GetMapping("/{page}/{limit}")
    public Result list(@PathVariable Long page,
                       @PathVariable Long limit,
                       SysUserQueryVo sysUserQueryVo){
        //create page obj
        Page<SysUser> pagePara = new Page<>(page,limit);
        //call service function
        IPage<SysUser> pageModel = sysUserService.selectPage(pagePara,sysUserQueryVo);
        return Result.ok(pageModel);
    }

    @ApiOperation("add user")
    @PostMapping("save")
    public Result save(@RequestBody SysUser sysUser){
        //encrypt with MD5
        String encrypt = MD5.encrypt(sysUser.getPassword());
        sysUser.setPassword(encrypt);
        boolean isSuccess = sysUserService.save(sysUser);
        if (isSuccess) {
            return Result.ok();
        }else {
            return Result.fail();
        }
    }

    @ApiOperation("get by Id")
    @GetMapping("getUser/{id}")
    public Result getUserById(@PathVariable String id){
        SysUser sysUserFound = sysUserService.getById(id);
        return Result.ok(sysUserFound);
    }

    @ApiOperation("update user")
    @PostMapping("update")
    public Result updateById(@RequestBody SysUser sysUser){
        boolean isSuccess = sysUserService.updateById(sysUser);
        if (isSuccess) {
            return Result.ok();
        }else {
            return Result.fail();
        }
    }

    @ApiOperation("delete user")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable String id){
        boolean isSuccess = sysUserService.removeById(id);
        if (isSuccess){
            return Result.ok();
        }else {
            return Result.fail();
        }
    }
}

