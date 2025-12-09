package com.personal.hjycommunitymodule.common.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.personal.hjycommunitymodule.common.serializer.FastJsonJsonRedisSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @ClassName RedisConfig
 * @Author liupanpan
 * @Date 2025/12/8
 * @Description Redis配置类
 */
@Configuration
public class RedisConfig {
    @Bean
    /*
    * @SuppressWarnings(value = {"unchecked","rawtypes"})：用于抑制编译器警告。
    * 因为我们在创建RedisTemplate时使用了原始类型（raw types）和未经检查的转换，所以会产生警告。这个注解告诉编译器忽略这些警告。
    * */
    @SuppressWarnings(value = {"unchecked","rawtypes"})
    public RedisTemplate<Object,Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();

        // 方法参数redisConnectionFactory由Spring容器自动注入，然后通过setConnectionFactory方法设置给RedisTemplate。
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        FastJsonJsonRedisSerializer serializer = new FastJsonJsonRedisSerializer(Object.class);
        ObjectMapper mapper = new ObjectMapper();
        // 指定要序列化的域
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        serializer.setObjectMapper(mapper);

        // 序列化redis中存储的value值,使用自定义的序列化工具
        redisTemplate.setValueSerializer(serializer);
        //redis中的key值,使用StringRedisSerializer来序列化和反序列化
        redisTemplate.setKeySerializer(new StringRedisSerializer());

        //初始化RedisTemplate的一些参数设置
        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }
}
