package com.personal.hjycommunitymodule.community.service;

import com.personal.hjycommunitymodule.common.core.domain.BaseResponse;
import com.personal.hjycommunitymodule.common.utils.ChainedMap;
import com.personal.hjycommunitymodule.community.domain.SysUser;
import com.personal.hjycommunitymodule.community.domain.vo.LoginBody;

/**
 * @ClassName LoginService
 * @Author liupanpan
 * @Date 2025/12/9
 * @Description
 */
public interface LoginService {

    BaseResponse login(LoginBody user);

    BaseResponse logout();

    ChainedMap getInfo();
}
