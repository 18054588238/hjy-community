package com.personal.hjycommunitymodule.common.serializer;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import io.jsonwebtoken.lang.Assert;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @ClassName FastJsonJsonRedisSerializer
 * @Author liupanpan
 * @Date 2025/12/9
 * @Description 序列化工具类，让redis使用FastJson序列化(GenericJackson2JsonRedisSerializer 使用 Jackson)，将value值存储为json格式，提高效率（默认使用的是JDK序列化，性能差，且存储的是二进制）
 */
public class FastJsonJsonRedisSerializer<T> implements RedisSerializer<T> {

    // ObjectMapper 提供 JSON 读写功能，用于对象和JSON之间的转换
    @SuppressWarnings("unused") // 表示忽略未使用的警告
    private ObjectMapper objectMapper = new ObjectMapper(); // 该类中并没有使用

    public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    private Class<T> clazz; // 用于在反序列化时指定目标类型

    static {
        /*
        * 设置FastJson的全局ParserConfig为自动类型支持（autoTypeSupport），
        * 这是为了在反序列化时能够自动识别类型。*/
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
    }
    public FastJsonJsonRedisSerializer(Class<T> clazz) {
        super(); // 如果没有显式调用super()，编译器会自动添加，所以这里写不写都可以
        this.clazz = clazz;
    }

    public void setObjectMapper(ObjectMapper objectMapper) {
        Assert.notNull(objectMapper,"'objectMapper' must not be null");
        this.objectMapper = objectMapper;
    }

    @Override
    public byte[] serialize(T t) throws SerializationException {
        if (t == null) {
            return new byte[0];
        }
        // SerializerFeature.WriteClassName,在序列化时输出类的全限定名,便于反序列化
        return JSON.toJSONString(t, SerializerFeature.WriteClassName).getBytes(DEFAULT_CHARSET);
    }

    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        String json = new String(bytes, DEFAULT_CHARSET);
        return JSON.parseObject(json,clazz);
    }

    /*
    * 将 Class 对象转换为 Jackson 的 JavaType 对象，用于处理泛型类型
    * */
    protected JavaType getJavaType(Class<?> clazz) {
        return TypeFactory.defaultInstance().constructType(clazz);
    }
}
