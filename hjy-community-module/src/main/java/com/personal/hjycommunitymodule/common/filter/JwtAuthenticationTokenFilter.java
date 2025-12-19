package com.personal.hjycommunitymodule.common.filter;

import com.alibaba.fastjson.JSON;
import com.personal.hjycommunitymodule.common.constant.Constants;
import com.personal.hjycommunitymodule.common.core.domain.BaseResponse;
import com.personal.hjycommunitymodule.common.core.exception.CustomException;
import com.personal.hjycommunitymodule.common.utils.JWTUtils;
import com.personal.hjycommunitymodule.common.utils.RedisCache;
import com.personal.hjycommunitymodule.common.utils.WebUtils;
import com.personal.hjycommunitymodule.community.domain.LoginUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.parallel.Execution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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

    @Value("${token.header}")
    private String header;

    private final List<String> excludeUrls = Arrays.asList(
            "/user/login","/captchaImage"
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
        String jwt = getToken(request);
        // 如果字符串不为空、长度大于 0 且不包含空白字符，则返回 true
        if (!StringUtils.hasText(jwt)) {
            // token为空，返回401未授权
            BaseResponse<Object> baseResponse = BaseResponse.fail(401, "未获取到token");
            WebUtils.renderString(response, JSON.toJSONString(baseResponse));
            // return的作用是返回响应的时候，避免走下面的逻辑
            return;
        }
        // 解析token
        String userId;
        try {
            Claims claims = JWTUtils.parserJWT(jwt);
            userId = claims.getSubject();
        } catch (Exception e) {
            BaseResponse<Object> baseResponse = BaseResponse.fail(401, "非法token");
            WebUtils.renderString(response, JSON.toJSONString(baseResponse));
//            throw new CustomException(401,"非法token");
            return;
        }

        // 从redis中获取用户信息
        String redisKey = Constants.LOGIN_USER_KEY+userId;
        Object object = redisCache.getCacheObject(redisKey);

        if (Objects.isNull(object)) {
            // 过滤器中抛出的异常直接返回给Servlet容器，不会进入Spring MVC
//            throw new CustomException(401,"用户未登录"); // 无法被@RestControllerAdvice捕获
            // 可以不处理，直接由Spring Security的异常处理机制抛出异常,也可以通过下面方式处理
//            BaseResponse<Object> baseResponse = BaseResponse.fail(401, "用户未登录");
//            WebUtils.renderString(response,JSON.toJSONString(baseResponse));
        } else {
            LoginUser user = (LoginUser) object;
            // 将 用户信息和权限信息 保存到SecurityContextHolder中 - 供后面的过滤器使用
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user,
                    null,
                    user.getAuthorities());
            // 设置与当前身份验证相关的详细信息（远程IP地址、会话id等）
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); // 当前请求的信息
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        // 放行
        filterChain.doFilter(request,response);
    }

    /** 获取请求携带的token */
    private String getToken(HttpServletRequest request) {
        String token = request.getHeader(header);
        if (org.apache.commons.lang3.StringUtils.isNotBlank(token) && token.startsWith(Constants.TOKEN_PREFIX)) {
            token = token.replace(Constants.TOKEN_PREFIX,""); // 去掉前缀
        }
        return token;
    }

    private boolean isExcludeUrl(String url) {
        return excludeUrls.stream().anyMatch(url::startsWith);
    }
}
