package com.atguigu.system.service.impl;

import com.atguigu.model.system.SysLoginLog;
import com.atguigu.model.vo.SysLoginLogQueryVo;
import com.atguigu.system.mapper.SysLoginLogMapper;
import com.atguigu.system.service.SysLoginLogService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mysql.cj.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysLoginLogServiceImpl implements SysLoginLogService {

    private final SysLoginLogMapper sysLoginLogMapper;
    @Autowired
    public SysLoginLogServiceImpl(SysLoginLogMapper sysLoginLogMapper) {
        this.sysLoginLogMapper = sysLoginLogMapper;
    }

    @Override
    public void recordLoginLog(String username,
                               Integer status,
                               String ipAddress,
                               String message) {
        SysLoginLog sysLoginLog = new SysLoginLog();
        sysLoginLog.setUsername(username);
        sysLoginLog.setStatus(status);
        sysLoginLog.setIpaddr(ipAddress);
        sysLoginLog.setMsg(message);
        sysLoginLogMapper.insert(sysLoginLog);
    }

    @Override
    public IPage<SysLoginLog> selectPage(long page, Long limit, SysLoginLogQueryVo sysLoginLogQueryVo) {
        //create page obj
        Page<SysLoginLog> pageParam = new Page<>(page,limit);
        //get condition value
        String username = sysLoginLogQueryVo.getUsername();
        String createTimeBegin = sysLoginLogQueryVo.getCreateTimeBegin();
        String createTimeEnd = sysLoginLogQueryVo.getCreateTimeEnd();
        QueryWrapper<SysLoginLog> wrapper = new QueryWrapper<>();
        if (!StringUtils.isNullOrEmpty(username)){
            wrapper.eq("username",username);
        }
        if (!StringUtils.isNullOrEmpty(createTimeBegin)){
            wrapper.ge("create_time",createTimeBegin);
        }
        if (!StringUtils.isNullOrEmpty(createTimeEnd)){
            wrapper.le("create_time",createTimeEnd);
        }
        //call mapper method
        IPage<SysLoginLog> pageModel = sysLoginLogMapper.selectPage(pageParam, wrapper);
        return pageModel;
    }
}
