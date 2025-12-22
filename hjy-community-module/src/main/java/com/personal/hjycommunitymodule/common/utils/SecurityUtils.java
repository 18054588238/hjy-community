package com.personal.hjycommunitymodule.common.utils;

import com.personal.hjycommunitymodule.common.core.exception.CustomException;
import com.personal.hjycommunitymodule.community.domain.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

/**
 * @ClassName SecurityUtils
 * @Author liupanpan
 * @Date 2025/12/22
 * @Description
 */
public class SecurityUtils {

    public static LoginUser getCurUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (Objects.isNull(authentication)) {
            throw new CustomException(401,"获取用户认证信息失败,请重新登录!");
        }
        return (LoginUser) authentication.getPrincipal();
    }
}
