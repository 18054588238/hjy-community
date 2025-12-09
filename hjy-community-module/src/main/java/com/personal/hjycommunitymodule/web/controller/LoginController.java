package com.personal.hjycommunitymodule.web.controller;

import com.personal.hjycommunitymodule.common.core.controller.BaseController;
import com.personal.hjycommunitymodule.common.core.domain.BaseResponse;
import com.personal.hjycommunitymodule.community.domain.SysUser;
import com.personal.hjycommunitymodule.community.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
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
        return loginService.login(user);
    }
}
