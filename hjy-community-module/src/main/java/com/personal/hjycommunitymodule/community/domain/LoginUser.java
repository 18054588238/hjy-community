package com.personal.hjycommunitymodule.community.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.personal.hjycommunitymodule.common.constant.UserConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName LoginUser
 * @Author liupanpan
 * @Date 2025/12/9
 * @Description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUser implements UserDetails {

    private SysUser sysUser;
    private long loginTime;
    private long expireTime;

    private List<String> permissions;

    private List<String> roles;

    /*权限信息通常只在后端使用，返回给前端用户信息时不需要包含权限列表
    * 当字段被标记为不序列化时，返回给前端的 JSON 数据中完全不会有这个字段*/
    @JSONField(serialize = false) // 被标注的字段在序列化（Java对象转JSON字符串）时会被跳过
    List<SimpleGrantedAuthority> authorities ;

    public LoginUser(SysUser sysUser, List<String> authorities) {
        this.sysUser = sysUser;
        this.permissions = authorities;
    }

    public LoginUser(SysUser sysUser, List<String> perms, List<String> roles) {

        this.sysUser = sysUser;
        this.permissions = perms;
        this.roles = roles;
        // 如果是admin用户，则拥有所有权限和角色
        if (sysUser.getUserId() == 1L) {
            this.permissions.add(UserConstants.ALL_PERMISSION);
//            this.roles.add(UserConstants.ALL_ROLE);
        }
    }

    // 1
    // 用于获取用户被授予的权限，可以用于实现访问控制。
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        // 获取授权信息、角色信息
        if (authorities == null) {
            authorities = permissions.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());

            roles.stream()
                    .map(i -> new SimpleGrantedAuthority("ROLE_" + i))
                    .forEach(authorities::add);
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return sysUser.getPassword();
    }

    @Override
    public String getUsername() {
        return sysUser.getUserName();
    }

    // 用于判断用户的账户是否未过期，可以用于实现账户有效期控制。
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 用于判断用户的账户是否未锁定，可以用于实现账户锁定功能。
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 用于判断用户的凭证（如密码）是否未过期，可以用于实现密码有效期控制。
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 用于判断用户是否已激活，可以用于实现账户激活功能。
    @Override
    public boolean isEnabled() {
        return true;
    }
}
