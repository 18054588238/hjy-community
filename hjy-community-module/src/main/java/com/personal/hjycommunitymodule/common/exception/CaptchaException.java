package com.personal.hjycommunitymodule.common.exception;

import com.personal.hjycommunitymodule.common.core.exception.CustomException;
import org.springframework.security.core.AuthenticationException;

/**
 * @ClassName CaptchaException
 * @Author liupanpan
 * @Date 2025/12/16
 * @Description
 */
public class CaptchaException extends CustomException {
    public CaptchaException() {
        super(400, "验证码错误");
    }
}
