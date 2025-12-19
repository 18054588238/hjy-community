package com.personal.hjycommunitymodule.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.personal.hjycommunitymodule.common.core.domain.UserStatus;
import com.personal.hjycommunitymodule.common.core.exception.BaseException;
import com.personal.hjycommunitymodule.community.domain.LoginUser;
import com.personal.hjycommunitymodule.community.domain.SysMenu;
import com.personal.hjycommunitymodule.community.domain.SysRole;
import com.personal.hjycommunitymodule.community.domain.SysUser;
import com.personal.hjycommunitymodule.community.mapper.SysMenuMapper;
import com.personal.hjycommunitymodule.community.mapper.SysRoleMapper;
import com.personal.hjycommunitymodule.community.mapper.SysUserMapper;
import com.personal.hjycommunitymodule.system.domain.SysDept;
import com.personal.hjycommunitymodule.system.mapper.SysDeptMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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
    @Autowired
    private SysDeptMapper deptMapper;

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
        // 数据库查询 存储授权信息
        List<SysMenu> perms = menuMapper.selectPermsByUserId(sysUser.getUserId());

        List<String> permsNameList = perms.stream()
                .map(SysMenu::getMenuName)
                .distinct()
                .collect(Collectors.toList());

        List<SysRole> roles = roleMapper.selectRolesByUserId(sysUser.getUserId());

        List<String> roleNameList = roles.stream()
                .map(SysRole::getRoleName)
                .distinct()
                .collect(Collectors.toList());

        List<Long> roleIds = roles.stream()
                .map(SysRole::getRoleId)
                .distinct()
                .collect(Collectors.toList());
        // 获取用户部门
        SysDept sysDept = deptMapper.selectById(sysUser.getDeptId());
        sysUser.setDept(sysDept);
        // 获取用户角色列表
        sysUser.setRoles(roles);
        // 获取用户角色id列表
        sysUser.setRoleIds(roleIds);
        return new LoginUser(sysUser,permsNameList,roleNameList);
    }
}
