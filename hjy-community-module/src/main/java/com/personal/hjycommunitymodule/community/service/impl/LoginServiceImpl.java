package com.personal.hjycommunitymodule.community.service.impl;

import com.personal.hjycommunitymodule.common.constant.Constants;
import com.personal.hjycommunitymodule.common.core.domain.BaseResponse;
import com.personal.hjycommunitymodule.common.core.exception.CustomException;
import com.personal.hjycommunitymodule.common.utils.JWTUtils;
import com.personal.hjycommunitymodule.common.utils.RedisCache;
import com.personal.hjycommunitymodule.community.domain.LoginUser;
import com.personal.hjycommunitymodule.community.domain.SysUser;
import com.personal.hjycommunitymodule.community.domain.vo.LoginBody;
import com.personal.hjycommunitymodule.community.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;

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
    private RedisCache redisCache;

    // 2
    @Override
    public BaseResponse login(LoginBody user) {

        // 校验验证码
        String key = Constants.CAPTCHA_CODE_KEY + user.getUuid();
        String code = redisCache.getCacheObject(key);
        redisCache.deleteObject(key);

        if (Objects.isNull(code) || !code.equalsIgnoreCase(user.getCode())) {
            throw new CustomException(400,"验证码错误");
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        // 调用(AuthenticationManager 实现类ProviderManager 委托给)DaoAuthenticationProvider的authenticate方法进行认证
        Authentication authenticate = authenticationManager.authenticate(authentication);
        if (authenticate == null) {
            throw new CustomException(401,"登录失败");// 认证失败
        }
        // 认证成功，使用userId生成token
        LoginUser principal = (LoginUser) authenticate.getPrincipal();
        Long userId = principal.getSysUser().getUserId();
        String jwt = JWTUtils.createJWT(String.valueOf(userId)); // 生成token

        // 将用户信息存储到redis缓存中
        redisCache.setCacheObject(Constants.LOGIN_USER_KEY+userId,principal);

        HashMap<String, String> map = new HashMap<>();
        map.put("token",jwt);
        return BaseResponse.success(map);
    }

    @Override
    public BaseResponse logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (Objects.isNull(authentication)) {
            throw new CustomException(401,"获取用户认证信息失败,请重新登录!");
        }
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userId = loginUser.getSysUser().getUserId();
        // 删除redis缓存
        boolean b = redisCache.deleteObject("login:" + userId);
        if (b) {
            return BaseResponse.success("已退出登录");
        }
        return BaseResponse.success("退出失败");
    }
}
