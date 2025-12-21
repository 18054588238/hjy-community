package com.personal.hjycommunitymodule;

import com.personal.hjycommunitymodule.community.domain.vo.MenuVo;
import com.personal.hjycommunitymodule.community.service.LoginService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.List;

@SpringBootTest
class HjyCommunityModuleApplicationTests {

    @Autowired
    LoginService loginService;

    @Test
    void contextLoads() {
        List<MenuVo> routers = loginService.getRouters();
        System.out.println(routers);
    }

    @Test
    void testJJWT() {
        long l = System.currentTimeMillis() + 10000000l;
        JwtBuilder builder = Jwts.builder()
                .setId("1128") // 设置jti (JWT ID) - payload
                .setSubject("hejiayun_community") // sub (subject) 设置主题 - payload
                .setIssuedAt(new Date()) // iat (issued at)设置颁发时间 - payload
                .setExpiration(new Date(l)) // 设置过期时间
                .claim("role", "admin") // 自定义载荷信息 ，以存储更多的信息
                .signWith(SignatureAlgorithm.HS256,"mashibing");// 设置签名
        // 构建 JWT 并根据 JWT 紧凑序列化规则将其序列化为紧凑的、URL 安全的字符串,签名后的jwt成为jws
        String jws = builder.compact();
        System.out.println(jws);

    }

    @Test
    void parserJWT() {
        String jws = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMTI4Iiwic3ViIjoiaGVqaWF5dW5fY29tbXVuaXR5IiwiaWF0IjoxNzY1MTg5NTgyLCJleHAiOjE3NjUxOTk1ODIsInJvbGUiOiJhZG1pbiJ9.Or_G2BBxOgClaf4fs3W-lzi18M8wTJ_d0KKJoQJy_WY";
        Claims claims = Jwts.parser()
                .setSigningKey("mashibing") // 密钥
                .parseClaimsJws(jws)
                .getBody();
        System.out.println(claims);// 输出payload中的内容
    }

    @Autowired
    PasswordEncoder passwordEncoder;
    @Test
    void encode() {
        String encode = passwordEncoder.encode("123456");
        System.out.println(encode);
    }
}
