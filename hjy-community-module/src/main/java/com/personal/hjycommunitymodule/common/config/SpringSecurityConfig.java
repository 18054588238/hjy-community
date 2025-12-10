package com.personal.hjycommunitymodule.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @ClassName SpringSecurityConfig
 * @Author liupanpan
 * @Date 2025/12/9
 * @Description
 */
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    // 编码方式
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 配置http请求的安全处理
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // 关闭csrf
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)// 不会创建会话，每个请求都将被视为独立的请求
                .and()
                .authorizeRequests() // 定义请求授权规则
                .antMatchers("/user/login").permitAll()//登录接口，允许匿名访问
                .anyRequest().authenticated()
        ;
//        super.configure(http);
    }

    // 注入AuthenticationManager，供外部使用
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
