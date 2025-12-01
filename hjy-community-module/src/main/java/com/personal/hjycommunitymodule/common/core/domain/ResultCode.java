package com.personal.hjycommunitymodule.common.core.domain;

import lombok.Data;

/**
 * @ClassName ResultCode
 * @Author liupanpan
 * @Date 2025/12/1
 * @Description 响应状态码
 */

public enum ResultCode {
    SUCCESS(200,"操作成功"),
    error(500,"操作失败");

    private Integer code;
    private String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
