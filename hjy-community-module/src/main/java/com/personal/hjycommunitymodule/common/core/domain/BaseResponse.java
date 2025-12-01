package com.personal.hjycommunitymodule.common.core.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName BaseResponse
 * @Author liupanpan
 * @Date 2025/12/1
 * @Description 统一接口响应对象
 */
@Data
public class BaseResponse<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer code;
    private String message;
    private T data;

    /*
    * 因为是静态方法（类级别的，不依赖于实例），不能使用类的类型参数，所以需要自己声明类型参数，<T> 在方法返回类型前声明
    * */
    public static <T> BaseResponse<T> success(T data) {
        BaseResponse<T> response = new BaseResponse<>();

        response.setCode(ResultCode.SUCCESS.getCode());
        response.setMessage(ResultCode.SUCCESS.getMessage());
        response.setData(data);

        return response;
    }

    public static <T> BaseResponse<T> fail(String message) {
        BaseResponse<T> response = new BaseResponse<>();

        response.setCode(ResultCode.error.getCode());
        response.setMessage(message);

        return response;
    }

    public static <T> BaseResponse<T> fail(Integer code, String message) {
        BaseResponse<T> response = new BaseResponse<>();

        response.setCode(code);
        response.setMessage(message);

        return response;
    }
}
