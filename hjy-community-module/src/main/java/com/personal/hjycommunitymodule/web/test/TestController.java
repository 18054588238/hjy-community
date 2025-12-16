package com.personal.hjycommunitymodule.web.test;

import com.personal.hjycommunitymodule.common.core.domain.BaseResponse;
import com.personal.hjycommunitymodule.common.core.exception.BaseException;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName TestController
 * @Author liupanpan
 * @Date 2025/12/1
 * @Description
 */
//@RestController
@Controller
public class TestController {

    @GetMapping("/test/user")
    public BaseResponse<User> test(String username){
        if (username == null){
            return BaseResponse.fail("username is null");
        }
        User test = new User(1, username);
        return BaseResponse.success(test);
    }

    @GetMapping("/test/valid")
    public BaseResponse valid(@Validated User user, BindingResult bindingResult){
        /* 检验失败的错误信息封装在BindingResult中 */

        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        System.out.println("校验错误信息:");
        for (FieldError fieldError : fieldErrors) {
            System.out.println(fieldError);
        }
        if (!fieldErrors.isEmpty()){
            throw new BaseException(500,"异常测试");
//            return BaseResponse.fail(fieldErrors.get(0).getDefaultMessage());
        }
        return BaseResponse.success(user);
    }

    @RequestMapping("/login.html")
    public String login() {
        return "login";
    }
}
