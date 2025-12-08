package com.personal.hjycommunitymodule;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
class HjyCommunityModuleApplicationTests {

    @Test
    void contextLoads() {

    }

    @Test
    void testJJWT() {
        JwtBuilder builder = Jwts.builder()
                .setId("1128") // 设置jti (JWT ID) - payload
                .setSubject("hejiayun_community") // sub (subject) 设置主题 - payload
                .setIssuedAt(new Date()) // iat (issued at)设置颁发时间 - payload
                .signWith(SignatureAlgorithm.HS256,"mashibing");// 设置签名
        // 构建 JWT 并根据 JWT 紧凑序列化规则将其序列化为紧凑的、URL 安全的字符串,签名后的jwt成为jws
        String jws = builder.compact();
        System.out.println(jws);

    }

    @Test
    void parserJWT() {

    }
}
