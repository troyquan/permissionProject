package com.atguigu.system.service.impl;

import com.atguigu.model.system.SysOperLog;
import com.atguigu.model.vo.SysOperLogQueryVo;
import com.atguigu.system.mapper.SysOperLogMapper;
import com.atguigu.system.service.OperLogService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mysql.cj.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysOperLogServiceImpl implements OperLogService {
    @Autowired
    private SysOperLogMapper sysOperLogMapper;
    @Override
    public void saveSysLog(SysOperLog sysOperLog) {
        sysOperLogMapper.insert(sysOperLog);
    }

    @Override
    public IPage<SysOperLog> selectPage(Long page, Long limit, SysOperLogQueryVo sysOperLogQueryVo) {
        Page<SysOperLog> pageParam = new Page<>(page,limit);
        //get condition
        String title = sysOperLogQueryVo.getTitle();
        String operName = sysOperLogQueryVo.getOperName();
        String createTimeBegin = sysOperLogQueryVo.getCreateTimeBegin();
        String createTimeEnd = sysOperLogQueryVo.getCreateTimeEnd();
        QueryWrapper<SysOperLog> wrapper =new QueryWrapper<>();
        if (!StringUtils.isNullOrEmpty(title)){
            wrapper.eq("title",title);
        }
        if (!StringUtils.isNullOrEmpty(operName)){
            wrapper.eq("oper_name",operName);
        }
        if (!StringUtils.isNullOrEmpty(createTimeBegin)){
            wrapper.ge("create_time",createTimeBegin);
        }
        if (!StringUtils.isNullOrEmpty(createTimeEnd)){
            wrapper.ge("create_time",createTimeEnd);
        }
        //call mapper method
        IPage<SysOperLog> pageModel = sysOperLogMapper.selectPage(pageParam, wrapper);
        return pageModel;
    }
}
