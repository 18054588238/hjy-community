package com.personal.hjycommunitymodule.common.core.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.servlet.http.HttpServletResponse;

/**
* @ClassName CustomException
* @Author liupanpan
* @Date 2025/12/16
* @Description 业务异常
*/
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomException extends RuntimeException {
    private Integer code;
    private String msg;
    private Object data;
    private Boolean success;

    public CustomException(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
        this.success = HttpServletResponse.SC_OK == code;
    }


}
