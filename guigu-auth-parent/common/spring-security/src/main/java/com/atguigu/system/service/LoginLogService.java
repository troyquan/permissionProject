package com.atguigu.system.service;

public interface LoginLogService {

    //log
    public void recordLoginLog(String username,
                               Integer status,
                               String ipAddress,
                               String message);


}
