package com.personal.hjycommunitymodule.common.core.exception;

import com.personal.hjycommunitymodule.common.core.domain.BaseResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

/**
 * @ClassName GlobalException
 * @Author liupanpan
 * @Date 2025/12/1
 * @Description 全局异常处理器
 */
@RestControllerAdvice  // 该注解是一个应用于Controller层的切面注解，一般配合@ExceptionHandler注解一起使用，作为项目的全局异常处理
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class) // 指定想处理的异常类型，然后在方法内编写对该异常的操作逻辑
    @ResponseBody
    public BaseResponse handleBaseException(BaseException e){
        return BaseResponse.fail(e.getDefaultMessage());
    }

    @ExceptionHandler(CustomException.class) // ← 这个注解只处理Controller抛出的异常，过滤器抛出的异常并不会到这里
    @ResponseBody
    public BaseResponse handleCustomException(CustomException e) {
        if (Objects.isNull(e.getCode())) {
            return BaseResponse.fail(e.getMsg());
        }
        return BaseResponse.fail(e.getCode(), e.getMsg(),e.getSuccess());
    }
}
