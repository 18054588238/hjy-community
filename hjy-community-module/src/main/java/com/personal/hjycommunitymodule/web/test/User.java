package com.personal.hjycommunitymodule.web.test;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

/**
 * @ClassName User
 * @Author liupanpan
 * @Date 2025/12/1
 * @Description
 */
@Data
@AllArgsConstructor
public class User {

    private Integer id;
    @NotBlank(message = "username不能为null")
    private String username;
}
