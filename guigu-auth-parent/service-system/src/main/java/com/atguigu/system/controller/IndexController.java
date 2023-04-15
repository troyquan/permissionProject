package com.atguigu.system.controller;

import com.atguigu.common.result.Result;
import com.atguigu.common.utils.JwtHelper;
import com.atguigu.common.utils.MD5;
import com.atguigu.model.system.SysUser;
import com.atguigu.model.vo.LoginVo;
import com.atguigu.system.exception.CustomizedException;
import com.atguigu.system.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Api("login")
@RestController
@RequestMapping("/admin/system/index")
public class IndexController {
    @Autowired
    private SysUserService sysUserService;
    //login
    @PostMapping("/login")
    public Result login(@RequestBody LoginVo loginVo) {
        //find by username
        SysUser userInfoByUserName = sysUserService.getUserInfoByUserName(loginVo.getUsername());
        System.out.println(userInfoByUserName);
        //if result is null
        if (userInfoByUserName == null) {
            throw new CustomizedException(20001,"Username not found");
        }
        //if psw is the same
        String password = loginVo.getPassword();
        String encrypt = MD5.encrypt(password);
        if (!userInfoByUserName.getPassword().equals(encrypt)) {
            throw new CustomizedException(20001,"Password is not correct");
        }

        if (userInfoByUserName.getStatus().intValue() == 0) {
            throw new CustomizedException(20001,"Account is banned, please contact amdin");
        }
        String token = JwtHelper.createToken(userInfoByUserName.getId(), userInfoByUserName.getUsername());

        //generate token
        Map<String, Object> map = new HashMap<>();
        map.put("token",token);
        System.out.println(map);
        return Result.ok(map);
    }
    @ApiOperation("get user info")
    @GetMapping("/info")
    public Result info(HttpServletRequest request) {
        System.out.println(request);
        //get token
        String token = request.getHeader("token");
        System.out.println("携带的token");
        System.out.println(token);
        //get user ID
        String username = JwtHelper.getUsername(token);
        //get info,menu permission and button permission by user ID
        Map<String,Object> map = sysUserService.getUserInfo(username);

        return Result.ok(map);
    }

    @PostMapping("/logout")
    public Result logout(){
        return Result.ok();
    }
}
