package com.personal.hjycommunitymodule.common.handler;

import com.alibaba.fastjson.JSON;
import com.personal.hjycommunitymodule.common.core.domain.BaseResponse;
import com.personal.hjycommunitymodule.common.core.exception.CustomException;
import com.personal.hjycommunitymodule.common.utils.JWTUtils;
import com.personal.hjycommunitymodule.common.utils.RedisCache;
import com.personal.hjycommunitymodule.common.utils.WebUtils;
import com.personal.hjycommunitymodule.community.domain.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @ClassName LogoutSuccessHandlerImpl
 * @Author liupanpan
 * @Date 2025/12/15
 * @Description 注销成功处理器
 */
@Component
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {
    @Autowired
    private RedisCache redisCache;
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if (Objects.isNull(authentication)) {
            throw new CustomException(401,"获取用户认证信息失败,请重新登录!");
        }
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userId = loginUser.getSysUser().getUserId();
        // 删除redis缓存
        redisCache.deleteObject("login:" + userId);

        BaseResponse<String> success = BaseResponse.success("注销成功");
        WebUtils.renderString(response, JSON.toJSONString(success));
    }
}
