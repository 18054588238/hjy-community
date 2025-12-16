package com.personal.hjycommunitymodule.community.domain.vo;

import lombok.Data;

/**
 * @ClassName LoginBody
 * @Author liupanpan
 * @Date 2025/12/16
 * @Description 用户登录对象
 */
@Data
public class LoginBody {
    private String username;
    private String password;
    private String code; // 验证码
    private String uuid = ""; // 唯一id
}
