package com.personal.hjycommunitymodule.common.core.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
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
    private String msg;
    private T data;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean success;

    /*
    * 因为是静态方法（类级别的，不依赖于实例），不能使用类的类型参数，所以需要自己声明类型参数，<T> 在方法返回类型前声明
    * */
    public static <T> BaseResponse<T> success(T data) {
        BaseResponse<T> response = new BaseResponse<>();

        response.setCode(ResultCode.SUCCESS.getCode());
        response.setMsg(ResultCode.SUCCESS.getMessage());
        response.setData(data);
//        response.setSuccess(true);

        return response;
    }

    public static <T> BaseResponse<T> success(T data,Boolean success) {
        BaseResponse<T> response = new BaseResponse<>();

        response.setCode(ResultCode.SUCCESS.getCode());
        response.setMsg(ResultCode.SUCCESS.getMessage());
        response.setData(data);
        response.setSuccess(success);

        return response;
    }

    public static <T> BaseResponse<T> fail(String message) {
        BaseResponse<T> response = new BaseResponse<>();

        response.setCode(ResultCode.error.getCode());
        response.setMsg(message);

        return response;
    }

    public static <T> BaseResponse<T> fail(Integer code, String message) {
        BaseResponse<T> response = new BaseResponse<>();

        response.setCode(code);
        response.setMsg(message);

        return response;
    }
    public static <T> BaseResponse<T> fail(Integer code, String message,Boolean success) {
        BaseResponse<T> response = new BaseResponse<>();

        response.setCode(code);
        response.setMsg(message);
        response.setSuccess(success);

        return response;
    }

}
