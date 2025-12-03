package com.personal.hjycommunitymodule.system.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName SysArea
 * @Author liupanpan
 * @Date 2025/12/3
 * @Description 区域信息
 */
@Data
public class SysArea implements Serializable {

    private static final long serialVersionUID = 1L;

    // 区划码
    private Integer code;
    // 区划名称
    private String name;
    // 上级区划码
    private Integer parentCode;
}
