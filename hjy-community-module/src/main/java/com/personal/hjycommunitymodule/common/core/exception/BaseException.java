package com.personal.hjycommunitymodule.common.core.exception;

import lombok.Getter;

/**
 * @ClassName BaseException
 * @Author liupanpan
 * @Date 2025/12/1
 * @Description 自定义异常类 - 基础异常
 */
@Getter
public class BaseException extends RuntimeException {
    // 错误码
    private Integer code;
    // 异常信息
    private String defaultMessage;

    public BaseException(Integer code,String defaultMessage) {
        super(defaultMessage);
        this.defaultMessage = defaultMessage;
        this.code = code;
    }
}
