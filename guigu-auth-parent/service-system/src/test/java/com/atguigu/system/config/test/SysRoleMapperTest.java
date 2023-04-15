package com.atguigu.system.config.test;

import com.atguigu.model.system.SysRole;
import com.atguigu.system.mapper.SysRoleMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class SysRoleMapperTest {
    @Autowired
    private SysRoleMapper sysRoleMapper;


    //select delete
    @Test
    public void testDelete(){
        QueryWrapper<SysRole> wrapper = new QueryWrapper<>();
        //set conditions
//        wrapper.eq("role_name","test");
        wrapper.like("role_name","test");
        sysRoleMapper.delete(wrapper);
    }


    //select
    @Test
    public void testSelect(){
        //create select constructor
        QueryWrapper<SysRole> wrapper = new QueryWrapper<>();
        //set conditions
//        wrapper.eq("role_name","test");
        wrapper.like("role_name","test");
        List<SysRole> list = sysRoleMapper.selectList(wrapper);
        System.out.println(list);

    }

    //deleteMany
    @Test
    public void deleteMany(){
        int row = sysRoleMapper.deleteBatchIds(Arrays.asList(1, 2));
    }

    //delete
    @Test
    public void deletById(){
        int row = sysRoleMapper.deleteById(8);
    }


    //update
    @Test
    public void update(){
        //find by id
        SysRole sysRole = sysRoleMapper.selectById(1);
        //set value
        sysRole.setDescription("testUpdate");
        //update with new value
        sysRoleMapper.updateById(sysRole);
    }


    //add
    @Test
    public void add(){
        SysRole sysRole = new SysRole();
        sysRole.setRoleName("test");
        sysRole.setRoleCode("test");
        sysRole.setDescription("test");
        int row = sysRoleMapper.insert(sysRole);
        System.out.println(row);
    }

    // find all
    @Test
    public void findAll(){
        List<SysRole> sysRolesList = sysRoleMapper.selectList(null);
        for (SysRole sysRole:sysRolesList){
            System.out.println(sysRole);
        }
    }
}
