package com.personal.hjycommunitymodule.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.personal.hjycommunitymodule.community.domain.LoginUser;
import com.personal.hjycommunitymodule.community.domain.SysUser;
import com.personal.hjycommunitymodule.community.mapper.SysMenuMapper;
import com.personal.hjycommunitymodule.community.mapper.SysRoleMapper;
import com.personal.hjycommunitymodule.community.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @ClassName UserDetailsService
 * @Author liupanpan
 * @Date 2025/12/9
 * @Description
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private SysUserMapper userMapper;
    @Autowired
    private SysMenuMapper menuMapper;
    @Autowired
    private SysRoleMapper roleMapper;

    // 1
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getUserName, username);
        SysUser sysUser = userMapper.selectOne(wrapper);
        if (sysUser == null) {
            throw new RuntimeException(("用户名或密码错误"));
        }
        // todo 数据库查询 存储授权信息
//        List<String> list = new ArrayList<>(Collections.singletonList("add"));
        List<String> perms = menuMapper.selectPermsByUserId(sysUser.getUserId());
        List<String> roles = roleMapper.selectRolesByUserId(sysUser.getUserId());
        return new LoginUser(sysUser,perms,roles);
    }
}
