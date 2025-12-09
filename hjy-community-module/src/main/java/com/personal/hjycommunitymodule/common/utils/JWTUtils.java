package com.personal.hjycommunitymodule.common.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import sun.misc.BASE64Encoder;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

/**
 * @ClassName JWTUtils
 * @Author liupanpan
 * @Date 2025/12/9
 * @Description JWT工具类
 */
public class JWTUtils {

    // 默认token过期时间
    public static final Long JWT_TTL = 60 * 60 *1000L; // 1h
    //秘钥明文
    public static final String JWT_KEY = "msbhjy";

    /**
     * 生成JWT
     * @param subject token中要存放的数据 - json格式
     * @return
     */
    public static String createJWT(String subject) {
        JwtBuilder jwtBuilder = builderJWT(subject,null,getUUID());
        return jwtBuilder.compact();
    }

    /**
     * 生成JWT
     * @param subject
     * @param ttlMillis token超时时间
     * @return
     */
    public static String createJWT(String subject,Long ttlMillis) {
        JwtBuilder jwtBuilder = builderJWT(subject,ttlMillis,getUUID());
        return jwtBuilder.compact();
    }

    public static String createJWT(String id,String subject,Long ttlMillis) {
        JwtBuilder jwtBuilder = builderJWT(subject,ttlMillis,id);
        return jwtBuilder.compact();
    }

    private static JwtBuilder builderJWT(String subject, Long ttlMillis, String uuid) {

        long currentTimeMillis = System.currentTimeMillis();
        if (ttlMillis == null) {
            ttlMillis = JWT_TTL;
        }
        long expireMillis = currentTimeMillis + ttlMillis;
        Date expireDate = new Date(expireMillis);

        // 生成秘钥
        SecretKey secretKey = generateSecretKey();

        return Jwts.builder()
                .setId(uuid) // 设置jti (JWT ID) - payload
                .setSubject(subject) // sub (subject) 设置主题 - payload
                .setIssuedAt(new Date()) // iat (issued at)设置颁发时间 - payload
                .setExpiration(expireDate) // 设置过期时间
                .signWith(SignatureAlgorithm.HS256,secretKey);// 设置签名
    }

    /**
     * 解析jwt
     * @param jwt
     * @return
     */
    public static Claims parserJWT(String jwt) {
        return Jwts.parser()
                .setSigningKey(generateSecretKey())
                .parseClaimsJws(jwt)
                .getBody();
    }

    /**
     * 生成加密后的秘钥
     * @return
     */
    private static SecretKey generateSecretKey() {
        byte[] decode = Base64.getDecoder().decode(JWT_KEY);
//        System.out.println("------------------"+decode);
        return new SecretKeySpec(decode, 0, decode.length, "AES");
    }

    private static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /*public static void main(String[] args) {
        String jwt = createJWT("123");
        System.out.println("jwt:"+jwt);
        Claims claims = parserJWT(jwt);
        System.out.println("claims:"+claims);
    }*/
}
