package com.personal.hjycommunitymodule.common.utils;

import com.personal.hjycommunitymodule.common.constant.Constants;
import io.jsonwebtoken.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
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



    @Value("${token.secret}")
    private static String secret; // 秘钥明文

    @Value("${token.expireTime}")
    private int expireTime; // 默认token过期时间

    // 默认token过期时间
    public static final Long JWT_TTL = 60 * 60 *1000L; // 1h
    //秘钥明文
//    public static final String JWT_KEY = "msbhjy";

    /**
     * 生成JWT
     * @param subject token中要存放的数据 - json格式
     * @return
     */
    public static String createJWT(String subject) {
        JwtBuilder jwtBuilder = builderJWT(subject,null,UUIDUtils.simpleUUID());
        return jwtBuilder.compact();
    }

    /**
     * 生成JWT
     * @param subject
     * @param ttlMillis token超时时间
     * @return
     */
    public static String createJWT(String subject,Long ttlMillis) {
        JwtBuilder jwtBuilder = builderJWT(subject,ttlMillis,UUIDUtils.simpleUUID());
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
    /** 刷新令牌 */
    public void refreshToken() {

    }


    /**
     * 生成加密后的秘钥
     * @return
     */
    private static SecretKey generateSecretKey() {
        byte[] decode = Base64.getDecoder().decode(secret);
//        System.out.println("------------------"+decode);
        return new SecretKeySpec(decode, 0, decode.length, "AES");
    }

    /*public static void main(String[] args) {
        String jwt = createJWT("123");
        System.out.println("jwt:"+jwt);
        Claims claims = parserJWT(jwt);
        System.out.println("claims:"+claims);
    }*/
}
