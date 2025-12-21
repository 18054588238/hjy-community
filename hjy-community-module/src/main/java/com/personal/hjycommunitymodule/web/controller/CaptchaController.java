package com.personal.hjycommunitymodule.web.controller;

import com.personal.hjycommunitymodule.common.constant.Constants;
import com.personal.hjycommunitymodule.common.core.domain.BaseResponse;
import com.personal.hjycommunitymodule.common.utils.ChainedMap;
import com.personal.hjycommunitymodule.common.utils.RedisCache;
import com.personal.hjycommunitymodule.common.utils.UUIDUtils;
import com.wf.captcha.SpecCaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName CaptchaController
 * @Author liupanpan
 * @Date 2025/12/16
 * @Description
 */
@RestController
public class CaptchaController {

    @Autowired
    private RedisCache redisCache;

    // 生成验证码
    @GetMapping("/captchaImage")
    public ChainedMap loadCode(HttpServletResponse response) {
        // 将生成的验证码和唯一id保存到redis中，并响应给前端
        SpecCaptcha captcha = new SpecCaptcha(130, 48, 4);
        String uuid = UUIDUtils.simpleUUID();
        String key = Constants.CAPTCHA_CODE_KEY + uuid;
        String verCode = captcha.text().toLowerCase();
        redisCache.setCacheObject(key,verCode, 5, TimeUnit.MINUTES);

        return ChainedMap.create().set("uuid",uuid).set("img",captcha.toBase64());
    }
}
