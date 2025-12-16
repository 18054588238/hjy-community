package com.personal.hjycommunitymodule.common.utils;

import java.util.UUID;

/**
 * @ClassName UUIDUtils
 * @Author liupanpan
 * @Date 2025/12/16
 * @Description UUID生成器工具类
 */
public class UUIDUtils {

    public static String getUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * 简化的UUID，去掉了横线
     *
     * @return 简化的UUID，去掉了横线
     */
    public static String simpleUUID()
    {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
