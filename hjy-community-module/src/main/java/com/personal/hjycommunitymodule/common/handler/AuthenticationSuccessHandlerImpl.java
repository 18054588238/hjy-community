package com.personal.hjycommunitymodule.common.handler;

import com.alibaba.fastjson.JSON;
import com.personal.hjycommunitymodule.common.core.domain.BaseResponse;
import com.personal.hjycommunitymodule.common.utils.WebUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName AuthenticationSuccessHandler
 * @Author liupanpan
 * @Date 2025/12/15
 * @Description 自定义认证成功处理器
 */
@Component
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

    // 当用户成功通过身份验证时调用 - 自动调用。
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        BaseResponse<String> result = BaseResponse.success("登录成功");

        WebUtils.renderString(response, JSON.toJSONString(result));
    }
}
