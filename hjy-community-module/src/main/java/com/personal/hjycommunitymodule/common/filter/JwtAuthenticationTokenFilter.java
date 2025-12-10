package com.personal.hjycommunitymodule.common.filter;

import com.personal.hjycommunitymodule.common.utils.JWTUtils;
import com.personal.hjycommunitymodule.common.utils.RedisCache;
import com.personal.hjycommunitymodule.community.domain.LoginUser;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.parallel.Execution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @ClassName JwtAuthenticationTokenFilter
 * @Author liupanpan
 * @Date 2025/12/10
 * @Description 自定义一个过滤器，放在UsernamePasswordAuthenticationFilter前面，
 * 获取请求头中的token，对token进行解析取出其中的userId（通过userId去redis获取用户信息、权限信息）
 * 继承OncePerRequestFilter，简化过滤器的编写，确保每个请求只被过滤一次，避免多次过滤的问题
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisCache redisCache;

    private final List<String> excludeUrls = Arrays.asList(
            "/user/login"
    );

    // 封装过滤器的执行逻辑
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getServletPath();
//        System.out.println("path:" + path);
        if (isExcludeUrl(path)) {
            filterChain.doFilter(request, response);// 登录路径放行
            return;
        }
        // 从请求头中获取token - 请求头名称是前后端约定好的
        // 使用"token"这个请求头来传递JWT令牌。前端在发送请求时，需要在请求头中设置名为"token"的字段，其值为JWT令牌。
        String jwt = request.getHeader("token");
        // 如果字符串不为空、长度大于 0 且不包含空白字符，则返回 true
        if (!StringUtils.hasText(jwt)) {
            // 放行
//            filterChain.doFilter(request,response);
            // token为空，返回401未授权
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Missing authentication token");
            // return的作用是返回响应的时候，避免走下面的逻辑
            return;
        }
        // 解析token
        String userId;
        try {
            Claims claims = JWTUtils.parserJWT(jwt);
            userId = claims.getSubject();
        } catch (Exception e) {
            throw new RuntimeException("非法token");
        }

        // 从redis中获取用户信息
        String redisKey = "login:"+userId;
        LoginUser user = redisCache.getCacheObject(redisKey);
        if (Objects.isNull(user)) {
            throw new RuntimeException("用户未登录");
        }
        // 将 用户信息和权限信息 保存到SecurityContextHolder中
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user,
                null,
                user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        // 放行
        filterChain.doFilter(request,response);
    }

    private boolean isExcludeUrl(String url) {
        return excludeUrls.stream().anyMatch(url::startsWith);
    }
}
