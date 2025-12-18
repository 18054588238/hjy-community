package com.personal.hjycommunitymodule.common.utils;

import com.personal.hjycommunitymodule.common.constant.Constants;
import com.personal.hjycommunitymodule.community.domain.LoginUser;
import io.jsonwebtoken.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName JWTUtils
 * @Author liupanpan
 * @Date 2025/12/9
 * @Description JWT工具类
 */
public class JWTUtils {

    // 注意 在静态字段上使用@Value注解，默认情况下是无法注入值的
//    @Value("${token.secret}")
//    private static String secret; // 秘钥明文

//    @Value("${token.expireTime}")
//    private static int expireTime; // 默认token过期时间

    private static final Long MILLISECOND = 1000L; // 秒
    private static final Long MILLISMINUTE = MILLISECOND * 60;// 分钟

    // 默认过期时间
    public static final Integer expireTime = 30;
    //秘钥明文
    public static final String secret = "msbhjy";

    /**
     * 生成JWT
     * @param loginUser
     * @return
     */
    public static String createJWT(LoginUser loginUser,RedisCache redisCache) {
        Long userId = loginUser.getSysUser().getUserId();
        JwtBuilder jwtBuilder = builderJWT(String.valueOf(userId),null,UUIDUtils.simpleUUID());
        refreshUserInfo(loginUser,redisCache);

        return jwtBuilder.compact();
    }

    /**
     * 生成JWT
     * @param subject token中要存放的数据 - json格式
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
    // 实际上是刷新redis中存储的用户信息
    public static void refreshUserInfo(LoginUser loginUser,RedisCache redisCache) {
        // 设置用户的登录时间和过期时间
        loginUser.setLoginTime(System.currentTimeMillis());
        loginUser.setExpireTime(loginUser.getLoginTime() + expireTime * MILLISMINUTE); // 30min
        // 重新缓存用户信息到redis中
        redisCache.setCacheObject(Constants.LOGIN_USER_KEY+loginUser.getSysUser().getUserId(),
                loginUser,expireTime, TimeUnit.MINUTES);
    }

    // token是从请求头中获取的，根据token中的userId从redis中获取用户信息
    // 实际上是验证的redis中存储的用户的有效期 - 不验证token有效期，token过期后需要重新登录
    public static boolean verifyUserInfo(LoginUser loginUser) {
        long expireTime1 = loginUser.getExpireTime();
        long currentTimeMillis = System.currentTimeMillis();
        return currentTimeMillis - expireTime1 > 0; // 当前时间大于过期时间，说明已经过期
    }

    // 获取当前登录用户信息
    /*public static LoginUser getLoginUser(HttpServletRequest request,RedisCache redisCache) {
        String token = request.getHeader(Constants.header);
        if (StringUtils.isBlank(token)) {
            return null;
        }
        Claims claims = parserJWT(token);
        String userId = claims.getSubject();
        redisCache.getCacheObject(Constants.LOGIN_USER_KEY+userId);

    }*/

    private static JwtBuilder builderJWT(String subject, Long ttlMillis, String uuid) {

        long currentTimeMillis = System.currentTimeMillis();
        if (ttlMillis == null) {
            ttlMillis = (expireTime+30) * MILLISMINUTE; // token过期时间1h
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
