package com.personal.hjycommunitymodule.web.controller;

import com.personal.hjycommunitymodule.common.core.controller.BaseController;
import com.personal.hjycommunitymodule.common.core.domain.BaseResponse;
import com.personal.hjycommunitymodule.community.domain.SysUser;
import com.personal.hjycommunitymodule.community.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName LoginController
 * @Author liupanpan
 * @Date 2025/12/9
 * @Description
 */
@RestController
public class LoginController extends BaseController {

    @Autowired
    LoginService loginService;

    @PostMapping("/user/login")
    public BaseResponse login(@RequestBody SysUser user) {
        System.out.println("---------");
        return loginService.login(user);
    }

    @PostMapping("/user/logout")
    public BaseResponse logout() {
        return loginService.logout();
    }

    @GetMapping("/hello")
    /*当使用 @PreAuthorize("hasAuthority('add1')") 注解时，
    Spring Security 会自动从 SecurityContext 中获取 Authentication 信息来评估权限
    客户端请求 → Spring Security 过滤器链 → SecurityContextHolder → Authentication → 权限评估*/
    @PreAuthorize("hasAuthority('add1')")
    public String hello() {
        return "hello";
    }
}
