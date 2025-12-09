package com.personal.hjycommunitymodule.community.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

    // 用于获取用户被授予的权限，可以用于实现访问控制。
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
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
