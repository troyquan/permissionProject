package com.atguigu.system.config.test;

import com.atguigu.model.system.SysRole;
import com.atguigu.system.mapper.SysRoleMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;


import java.util.ArrayList;
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
        wrapper.like("role_name","testClass");

        sysRoleMapper.delete(wrapper);
        SysRoleMapper mockMapper = Mockito.mock(SysRoleMapper.class);
        int expectedRows = 8; // set the expected return value to 3 for example
        Mockito.when(mockMapper.delete(wrapper)).thenReturn(expectedRows);

        int actualRows = mockMapper.delete(wrapper);

        // verify that the delete method was called with the expected wrapper
        Mockito.verify(mockMapper).delete(wrapper);

        // verify that the actual number of rows deleted matches the expected number of rows deleted
        assertEquals(expectedRows,actualRows);
    }


    //select
    @Test
    public void testSelect(){
        QueryWrapper<SysRole> wrapper = new QueryWrapper<>();
        wrapper.like("role_name", "testClass");

        // mock the mapper and set the expected return value
        SysRoleMapper mockMapper = Mockito.mock(SysRoleMapper.class);
        List<SysRole> expectedList = new ArrayList<>();
        SysRole role1 = new SysRole();
        role1.setRoleId(1L);
        role1.setRoleName("testClass1");
        SysRole role2 = new SysRole();
        role2.setRoleId(2L);
        role2.setRoleName("testClass2");
        expectedList.add(role1);
        expectedList.add(role2);
        Mockito.when(mockMapper.selectList(wrapper)).thenReturn(expectedList);

        List<SysRole> actualList = mockMapper.selectList(wrapper);

        // verify that the selectList method was called with the expected wrapper
        Mockito.verify(mockMapper).selectList(wrapper);

        // verify that the actual list size matches the expected list size
        assertEquals(expectedList,actualList);

        // verify that each role in the actual list matches the corresponding role in the expected list
        for (int i = 0; i < expectedList.size(); i++) {
            SysRole expectedRole = expectedList.get(i);
            SysRole actualRole = actualList.get(i);
            assertEquals(expectedRole,actualRole);
        }
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
