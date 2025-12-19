package com.personal.hjycommunitymodule.web.controller;

import com.personal.hjycommunitymodule.common.core.controller.BaseController;
import com.personal.hjycommunitymodule.common.core.domain.BaseResponse;
import com.personal.hjycommunitymodule.common.utils.ChainedMap;
import com.personal.hjycommunitymodule.community.domain.SysUser;
import com.personal.hjycommunitymodule.community.domain.vo.LoginBody;
import com.personal.hjycommunitymodule.community.domain.vo.MenuVo;
import com.personal.hjycommunitymodule.community.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public BaseResponse login(@RequestBody LoginBody user) {
        System.out.println("---------");
        return loginService.login(user);
    }

    // 获取用户信息，以及对应的权限信息、角色信息
    @GetMapping("/getInfo")
    public ChainedMap getInfo() {
        return loginService.getInfo();
    }

    @GetMapping("/getRouters")
    public BaseResponse getRouters(){
        List<MenuVo> data = loginService.getRouters();
        return BaseResponse.success(data);
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

    @GetMapping("/test")
    @PreAuthorize("hasAnyAuthority('小区信息','add1')")
    public String test() {
        return "test";
    }

    @GetMapping("/role")
    // 内部会把传入的参数拼接上ROLE_再去比较，所以从数据库查询出来的角色在比较之前也要加上ROLE_
    @PreAuthorize("hasAnyRole('普通用户','超级管理员')")
    public String role() {
        return "role";
    }

    @GetMapping("/myPerm")
    // 使用自定义权限校验
    @PreAuthorize("@my_ex.hasAuthority('小区信息')")
    public String myPerm() {
        return "myPerm";
    }

    @GetMapping("/config")
    // 通过配置方式进行权限校验
    public String config() {
        return "config";
    }

    @GetMapping("/roleAndPerm1")
    // 角色加权限校验
    @PreAuthorize("hasRole('普通角色') and hasAnyAuthority('小区信息','add1')")
    public String roleAndPerm1() {
        return "roleAndPerm1";
    }

    @GetMapping("/roleAndPerm2")
    // 角色加权限校验
    @PreAuthorize("hasRole('超级管理员') or hasAuthority('add1')")
    public String roleAndPerm2() {
        return "roleAndPerm2";
    }

    @PostMapping("/testCors")
    public BaseResponse testCors() {
        return BaseResponse.success("testCors");
    }
}
