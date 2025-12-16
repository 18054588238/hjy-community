package com.personal.hjycommunitymodule.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.personal.hjycommunitymodule.common.core.domain.UserStatus;
import com.personal.hjycommunitymodule.common.core.exception.BaseException;
import com.personal.hjycommunitymodule.community.domain.LoginUser;
import com.personal.hjycommunitymodule.community.domain.SysUser;
import com.personal.hjycommunitymodule.community.mapper.SysMenuMapper;
import com.personal.hjycommunitymodule.community.mapper.SysRoleMapper;
import com.personal.hjycommunitymodule.community.mapper.SysUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @ClassName UserDetailsService
 * @Author liupanpan
 * @Date 2025/12/9
 * @Description
 */
@Service
@Slf4j
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

        if (Objects.isNull(sysUser)) {
            log.info("用户：{}不存在",username);
            throw new UsernameNotFoundException("用户："+username+"不存在");
        }
        if (UserStatus.DISABLE.getCode().equals(sysUser.getStatus())) {
            log.info("用户：{}已被停用",username);
            throw new BaseException("用户："+username+"已被停用");
        }
        if (UserStatus.DELETED.getCode().equals(sysUser.getDelFlag())) {
            log.info("用户：{}已被删除",username);
            throw new BaseException("用户："+username+"已被删除");
        }
        // todo 数据库查询 存储授权信息
        List<String> perms = menuMapper.selectPermsByUserId(sysUser.getUserId());
        List<String> roles = roleMapper.selectRolesByUserId(sysUser.getUserId());
        return new LoginUser(sysUser,perms,roles);
    }
}
