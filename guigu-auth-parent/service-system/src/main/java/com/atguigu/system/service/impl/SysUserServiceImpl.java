package com.atguigu.system.service.impl;

import com.atguigu.model.system.SysUser;
import com.atguigu.model.vo.RouterVo;
import com.atguigu.model.vo.SysUserQueryVo;
import com.atguigu.system.mapper.SysUserMapper;
import com.atguigu.system.service.SysMenuService;
import com.atguigu.system.service.SysUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author Yongze
 * @since 2023-04-08
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
    @Autowired
    private SysMenuService sysMenuService;
    //pagination
    @Override
    public IPage<SysUser> selectPage(Page<SysUser> pagePara, SysUserQueryVo sysUserQueryVo) {
        return baseMapper.selectPage(pagePara,sysUserQueryVo);
    }
    //change user status
    @Override
    public void updateStatus(String id, Integer status) {
        //get by id
        SysUser sysUser = baseMapper.selectById(id);
        //set status
        sysUser.setStatus(status);
        //call function update
        baseMapper.updateById(sysUser);
    }

    @Override
    public SysUser getUserInfoByUserName(String username) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        return baseMapper.selectOne(wrapper);
    }

    @Override
    public Map<String, Object> getUserInfo(String username) {
        System.out.println(username);
        //get user info
        SysUser sysUser = this.getUserInfoByUserName(username);
        System.out.println(sysUser);
        //get menu
        List<RouterVo> routerVoList = sysMenuService.getUserMenuList(sysUser.getId());
        //get button
        List<String> permsList =sysMenuService.getUserButton(sysUser.getId());

        Map<String,Object> result = new HashMap<>();
        result.put("name",username);
        result.put("avatar","");
        result.put("roles","[admin]");
        result.put("routers",routerVoList);
        result.put("button",permsList);

        return result;
    }
}
