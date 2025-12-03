package com.personal.hjycommunitymodule.community.domain;

import lombok.Data;

/**
 * @ClassName SysArea
 * @Author liupanpan
 * @Date 2025/12/3
 * @Description 区域信息
 */
@Data
public class SysArea {
    // 区划码
    private Integer code;
    // 区划名称
    private String name;
    // 上级区划码
    private Integer parentCode;
}
