package com.personal.hjycommunitymodule.common.config;

import com.personal.hjycommunitymodule.common.exception.AccessDeniedHandlerImpl;
import com.personal.hjycommunitymodule.common.exception.AuthenticationEntryPointImpl;
import com.personal.hjycommunitymodule.common.filter.JwtAuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

/**
 * @ClassName SpringSecurityConfig
 * @Author liupanpan
 * @Date 2025/12/9
 * @Description
 */
/*允许在方法上使用注解来定义访问控制规则
* prePostEnabled = true：这个属性表示启用Spring Security的Pre/Post注解，
* 即@PreAuthorize、@PostAuthorize、@PreFilter和@PostFilter*/
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
    @Autowired
    private AccessDeniedHandlerImpl accessDeniedHandler;
    @Autowired
    private AuthenticationEntryPointImpl authenticationEntryPoint;
    @Autowired
    private AuthenticationSuccessHandler successHandler;
    @Autowired
    private AuthenticationFailureHandler failureHandler;
    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;

    // 编码方式
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 3
    // 配置http请求的安全处理
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 关闭csrf - 前后端分离的 RESTful API必须禁用csrf，前端不是通过表单提交，而是 AJAX/Fetch
        //不使用 Session，而是 JWT/OAuth 等无状态认证
        http.csrf().disable();
        // 允许跨域 - 项目资源受到spring security保护，所以要在spring security设置允许跨域
        http.cors();

        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)// 不会创建会话，每个请求都将被视为独立的请求
                .and()
                .authorizeRequests() // 定义请求授权规则
                .antMatchers("/user/login").permitAll()//登录接口，允许匿名访问
                .antMatchers("/config").hasAuthority("小区信息") // 配置形式的权限控制
                .anyRequest().authenticated()
        ;
//        http.authorizeHttpRequests()
//                .mvcMatchers("/hejiayun/login.html","/hejiayun/test/login").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin()
//                .loginPage("/hejiayun/login.html")
//                .loginProcessingUrl("/hejiayun/login")
//                .usernameParameter("username")
//                .passwordParameter("password")
//                .successHandler(successHandler)
//                .failureHandler(failureHandler)
//                .and()
//                .logout()
//                .invalidateHttpSession(true) // 在注销时使 HttpSession 失效
//                .clearAuthentication(true) // 退出时清除认证信息
//                .logoutSuccessHandler(logoutSuccessHandler);
        // 将自定义认证过滤器，添加到过滤器链中
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

        // 配置认证、授权失败处理器
        http.exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler);

    }

    // 注入AuthenticationManager，供外部使用
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
