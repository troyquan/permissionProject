package com.atguigu.system.controller;

import com.atguigu.common.result.Result;
import com.atguigu.model.system.SysOperLog;
import com.atguigu.model.vo.SysOperLogQueryVo;
import com.atguigu.system.service.OperLogService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "SysOperLog Manage", tags = "SysOperLog Manage")
@RestController
@RequestMapping(value="/admin/system/sysOperLog")
public class SysOperLogController {
    @Autowired
    private OperLogService operLogService;
    @PreAuthorize("hasAuthority('bnt.sysOperLog.list')")
    @ApiOperation(value = "Pagination List")
    @GetMapping("{page}/{limit}")
    public Result index(
            @ApiParam(name = "page", value = "present page", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "page limit", required = true)
            @PathVariable Long limit,

            @ApiParam(name = "sysOperLogVo", value = "searchObj", required = false)
            SysOperLogQueryVo sysOperLogQueryVo) {
           IPage<SysOperLog> pageModel =  operLogService.selectPage(page,limit,sysOperLogQueryVo);
        System.out.println(sysOperLogQueryVo);
        return Result.ok(pageModel);
    }


}
