package com.personal.hjycommunitymodule.community.service.impl;

import com.personal.hjycommunitymodule.common.core.domain.BaseResponse;
import com.personal.hjycommunitymodule.community.domain.SysUser;
import com.personal.hjycommunitymodule.community.service.LoginService;
import org.springframework.stereotype.Service;

/**
 * @ClassName LoginServiceImpl
 * @Author liupanpan
 * @Date 2025/12/9
 * @Description
 */
@Service
public class LoginServiceImpl implements LoginService {
    @Override
    public BaseResponse login(SysUser user) {
        // 调用AuthenticationManager中DaoAuthenticationProvider的authenticate方法进行认证

        return null;
    }
}
