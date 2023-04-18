package com.atguigu.system.mapper;

import com.atguigu.model.system.SysLoginLog;
import com.atguigu.model.system.SysOperLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface SysOperLogMapper extends BaseMapper<SysOperLog> {

}
