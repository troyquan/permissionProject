package com.atguigu.system.config.test;

import com.atguigu.model.system.SysRole;
import com.atguigu.system.service.SysRoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class SysRoleServiceTest {
    @Autowired
    private SysRoleService sysRoleService;

    @Test
    public void findAll(){
        List<SysRole> list = sysRoleService.list();
        System.out.println(list);
    }

    @Test
    public void add(){
        SysRole sysRole = new SysRole();
        sysRole.setRoleName("service test");
        sysRole.setRoleCode("service test");
        sysRole.setDescription("service test");
        sysRoleService.save(sysRole);


    }

    @Test
    public void update(){
        SysRole roleFind = sysRoleService.getById(1);
        roleFind.setDescription("update");
        sysRoleService.updateById(roleFind);
    }

    @Test
    public void remove(){
        sysRoleService.removeById(4);
    }

    @Test
    public void select(){
        QueryWrapper<SysRole> wrapper = new QueryWrapper<>();
        wrapper.eq("role_code","select test");
        List<SysRole> list = sysRoleService.list(wrapper);
        System.out.println(list);
    }
}
