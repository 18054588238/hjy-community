package com.personal.hjycommunitymodule.common.utils;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.metadata.ClassMapBuilder;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName OrikaUtils
 * @Author liupanpan
 * @Date 2025/12/5
 * @Description 对象复制工具类
 */
public class OrikaUtils {
    // 构造一个MapperFactory，该工厂用于创建映射器
    private static final MapperFactory FACTORY = new DefaultMapperFactory.Builder().build();

    /*缓存实例集合，缓存已经配置好的映射器，避免重复创建*/
    private static final Map<String, MapperFacade> MAPPER_CACHE = new ConcurrentHashMap<>();

    // 每个实例持有的映射器门面
    private final MapperFacade mapper;

    public OrikaUtils(MapperFacade mapper) {
        this.mapper = mapper;
    }

    /**
     * 字段不一致实体转换函数（需要字段映射）
     * @param source
     * @param targetClass
     * @param refMap 配置源类与目标类不同字段名映射
     * @return 目标类实体
     * @param <S>
     * @param <T>
     */
    public static <S,T> T convert(S source, Class<T> targetClass,Map<String,String> refMap) {
        if (source == null) {
            return null;
        }
        // 获取或创建映射器，然后执行映射
        return classMap(source.getClass(),targetClass,refMap).map(source,targetClass);
    }

    /**
     * 字段一致实体转换
     * @param source
     * @param targetClass
     * @return
     * @param <S>
     * @param <T>
     */
    public static <S,T> T convert(S source, Class<T> targetClass) {
        return convert(source,targetClass,null);
    }

    /**
     * 字段一致集合转换
     * @param sourceList
     * @param targetClass
     * @return
     * @param <S>
     * @param <T>
     */
    public static <S,T> List<T> convertList(List<S> sourceList,Class<T> targetClass) {
        return convertList(sourceList,targetClass,null);
    }

    /**
     * 字段不一致集合转换
     * @param sourceList
     * @param targetClass
     * @param refMap
     * @return
     * @param <S>
     * @param <T>
     */
    public static <S,T> List<T> convertList(List<S> sourceList,Class<T> targetClass,Map<String,String> refMap) {
        if (sourceList == null) {
            return null;
        }
        if (sourceList.isEmpty()) {
            return new ArrayList<>(0);
        }
        // 使用列表第一个元素获取源类型，然后获取映射器并执行列表映射
        return classMap(sourceList.get(0).getClass(),targetClass,refMap).mapAsList(sourceList,targetClass);
    }

    /**
     * 复制list
     * @param sourceList
     * @param targetClass
     * @return
     * @param <T>
     * @param <S>
     */
    private <T, S> List<T> mapAsList(List<S> sourceList, Class<T> targetClass) {
        return CollectionUtils.isEmpty(sourceList) ? Collections.emptyList():mapper.mapAsList(sourceList,targetClass);
    }

    /**
     * 核心映射配置方法
     * 属性名称不一致时使用
     * synchronized：确保线程安全，防止重复创建相同类型的映射器
     * @param sourceClass
     * @param targetClass
     * @param refMap
     * @return
     * @param <V>
     * @param <P>
     */
    public static/* synchronized*/ <V,P> OrikaUtils classMap(Class<V> sourceClass, Class<P> targetClass,Map<String,String> refMap) {
        // 生成缓存key：使用规范类名
        String key = sourceClass.getCanonicalName() +":"+targetClass.getCanonicalName(); // getCanonicalName()：返回 Java 语言规范中定义的底层类的规范名称。比getName()更稳定
//        // 使用computeIfAbsent优化synchronized带来的性能问题
//        MapperFacade mapperFacade = MAPPER_CACHE.computeIfAbsent(key, k -> {
//            // 注册新的映射配置
//            register(sourceClass, targetClass, refMap);
//            return FACTORY.getMapperFacade();
//        });

        if (MAPPER_CACHE.containsKey(key)) {
            return new OrikaUtils(MAPPER_CACHE.get(key));
        }
        // 注册新的映射配置
        register(sourceClass,targetClass,refMap);
        // 从工厂获取映射器门面
        MapperFacade mapperFacade = FACTORY.getMapperFacade();
        MAPPER_CACHE.put(key,mapperFacade); // 放入缓存
        return new OrikaUtils(mapperFacade);
    }

    /**
     * 属性名称一致时使用
     * @param sourceClass
     * @param targetClass
     * @return
     * @param <V>
     * @param <P>
     */
    public static <V,P> OrikaUtils classMap(Class<V> sourceClass, Class<P> targetClass) {
        return classMap(sourceClass,targetClass,null);
    }

    /**
     * 字段属性转换
     * @param sourceClass
     * @param targetClass
     * @param refMap
     * @param <V>
     * @param <P>
     */
    private static <V, P> void register(Class<V> sourceClass, Class<P> targetClass, Map<String, String> refMap) {
        if (CollectionUtils.isEmpty(refMap)) {
            // 注册默认映射（同名字段自动映射）
            FACTORY.classMap(sourceClass, targetClass).byDefault().register();
        } else {
            // 有字段映射配置，创建ClassMapBuilder
            ClassMapBuilder<V, P> classMapBuilder = FACTORY.classMap(sourceClass, targetClass);
            // 遍历refMap，配置字段映射关系
            refMap.forEach(classMapBuilder::field);
            // 注册同名字段映射
            classMapBuilder.byDefault().register();
        }
    }

    /**
     * Orika复制对象
     * @param source
     * @param targetClass
     * @return
     * @param <T>
     * @param <S>
     */
    private <T, S> T map(S source, Class<T> targetClass) {
        return mapper.map(source,targetClass);
    }
}
