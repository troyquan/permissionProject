package com.atguigu.system.controller;

import com.atguigu.common.result.Result;
import com.atguigu.model.system.SysLoginLog;
import com.atguigu.model.vo.SysLoginLogQueryVo;
import com.atguigu.system.service.SysLoginLogService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "SysLoginLog Manage", tags = "SysLoginLog Manage")
@RestController
@RequestMapping(value="/admin/system/sysLoginLog")
public class SysLoginLogController {
    @Autowired
    private SysLoginLogService sysLoginLogService;

    //pagination login log
    @ApiOperation(value = "Get Pagination List")
    @GetMapping("{page}/{limit}")
    public Result index(
            @ApiParam(name = "page", value = "present page", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "page limit", required = true)
            @PathVariable Long limit,

            @ApiParam(name = "sysLoginLogVo", value = "searchObj", required = false)
            SysLoginLogQueryVo sysLoginLogQueryVo) {
//        Page<SysLoginLog> pageParam = new Page<>(page, limit);
        IPage<SysLoginLog> pageModel = sysLoginLogService.selectPage(page,limit, sysLoginLogQueryVo);

        return Result.ok(pageModel);
    }
}
