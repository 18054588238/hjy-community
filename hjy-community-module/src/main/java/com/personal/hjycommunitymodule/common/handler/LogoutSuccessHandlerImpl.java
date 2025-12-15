package com.personal.hjycommunitymodule.common.handler;

import com.alibaba.fastjson.JSON;
import com.personal.hjycommunitymodule.common.core.domain.BaseResponse;
import com.personal.hjycommunitymodule.common.utils.WebUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName LogoutSuccessHandlerImpl
 * @Author liupanpan
 * @Date 2025/12/15
 * @Description 注销成功处理器
 */
@Component
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        BaseResponse<String> success = BaseResponse.success("注销成功");
        WebUtils.renderString(response, JSON.toJSONString(success));
    }
}
