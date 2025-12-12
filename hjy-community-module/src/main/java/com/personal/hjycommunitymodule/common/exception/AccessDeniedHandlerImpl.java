package com.personal.hjycommunitymodule.common.exception;

import com.alibaba.fastjson.JSON;
import com.personal.hjycommunitymodule.common.core.domain.BaseResponse;
import com.personal.hjycommunitymodule.common.utils.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName AccessDeniedHandlerImpl
 * @Author liupanpan
 * @Date 2025/12/12
 * @Description
 * 自定义异常处理逻辑 - 授权过程的异常处理
 */
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        BaseResponse<Object> baseResponse = BaseResponse.fail(HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN.getReasonPhrase() + "无权限");// Forbidden无权限
        WebUtils.renderString(response, JSON.toJSONString(baseResponse));
    }
}
