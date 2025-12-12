package com.personal.hjycommunitymodule.common.exception;

import com.alibaba.fastjson.JSON;
import com.personal.hjycommunitymodule.common.core.domain.BaseResponse;
import com.personal.hjycommunitymodule.common.utils.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName AuthenticationEntryPointImpl
 * @Author liupanpan
 * @Date 2025/12/12
 * @Description
 * 自定义异常处理逻辑 - 认证过程的异常处理
 * AuthenticationEntryPoint 用于处理未经身份验证的用户访问受保护资源时抛出的异常
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        BaseResponse<Object> baseResponse = BaseResponse.fail(HttpStatus.UNAUTHORIZED.value(), "认证失败请重新登录");

        WebUtils.renderString(response, JSON.toJSONString(baseResponse));// 渲染到前端
    }
}
