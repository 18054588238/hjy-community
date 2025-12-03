package com.personal.hjycommunitymodule.system.domain.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName SysAreaDto
 * @Author liupanpan
 * @Date 2025/12/3
 * @Description 区域层级模型 - 树结构
 */
@Data
@Builder
public class SysAreaDto implements Serializable {

    private static final long serialVersionUID = -2353247612831790287L;

    // 区划码
    private Integer code;
    // 区划名称
    private String name;
    // 子级区划码
    private List<SysAreaDto> children;
}
