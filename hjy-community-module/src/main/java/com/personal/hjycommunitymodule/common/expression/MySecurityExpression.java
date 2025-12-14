package com.personal.hjycommunitymodule.common.expression;

import com.personal.hjycommunitymodule.community.domain.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

/**
 * @ClassName MySecurityExpression
 * @Author liupanpan
 * @Date 2025/12/14
 * @Description 自定义权限校验
 */
@Component("my_ex")
public class MySecurityExpression {
    public boolean hasAuthority(String authority) {
        // 获取当前用户权限信息（来源：数据库）
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        List<String> permissions = loginUser.getPermissions();
        // 判断传入的权限是否在列表中
        return permissions.contains(authority);
    }
}
