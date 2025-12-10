package com.personal.hjycommunitymodule.community.service.impl;

import com.personal.hjycommunitymodule.common.core.domain.BaseResponse;
import com.personal.hjycommunitymodule.common.utils.JWTUtils;
import com.personal.hjycommunitymodule.common.utils.RedisCache;
import com.personal.hjycommunitymodule.community.domain.LoginUser;
import com.personal.hjycommunitymodule.community.domain.SysUser;
import com.personal.hjycommunitymodule.community.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @ClassName LoginServiceImpl
 * @Author liupanpan
 * @Date 2025/12/9
 * @Description
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    RedisCache redisCache;

    @Override
    public BaseResponse login(SysUser user) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        // 调用(AuthenticationManager 实现类ProviderManager 委托给)DaoAuthenticationProvider的authenticate方法进行认证
        Authentication authenticate = authenticationManager.authenticate(authentication);
        if (authenticate == null) {
            throw new RuntimeException("登录失败");// 认证失败
        }
        // 认证成功，使用userId生成token
        LoginUser principal = (LoginUser) authenticate.getPrincipal();
        Long userId = principal.getSysUser().getUserId();
        String jwt = JWTUtils.createJWT(String.valueOf(userId));

        // 将用户信息存储到redis缓存中
        redisCache.setCacheObject("login:"+userId,principal);

        HashMap<String, String> map = new HashMap<>();
        map.put("token",jwt);
        return BaseResponse.success(map);
    }
}
