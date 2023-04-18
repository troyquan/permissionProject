package com.atguigu.system.service;

import com.atguigu.model.system.SysLoginLog;
import com.atguigu.model.vo.SysLoginLogQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;

public interface SysLoginLogService {

    //log
    public void recordLoginLog(String username,
                               Integer status,
                               String ipAddress,
                               String message);


     IPage<SysLoginLog> selectPage(long page, Long limit, SysLoginLogQueryVo sysLoginLogQueryVo);
}
