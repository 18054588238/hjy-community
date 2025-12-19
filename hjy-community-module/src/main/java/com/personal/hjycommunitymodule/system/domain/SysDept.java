package com.personal.hjycommunitymodule.system.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.personal.hjycommunitymodule.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 部门表(SysDept)表实体类
 *
 * @author makejava
 * @since 2025-12-03 21:10:07
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysDept extends BaseEntity {
//部门id
    @TableId
    private Long deptId;
//父部门id
    private Long parentId;
    // 添加一个父部门名称
    @TableField(exist = false)
    private String parentName;
    // 添加一个子部门属性
    @TableField(exist = false)
    private List<SysDept> children;
//祖级列表
    private String ancestors;
//部门名称
    private String deptName;

//显示顺序
    private Integer orderNum;
//负责人
    private String leader;
//联系电话
    private String phone;
//邮箱
    private String email;
//部门状态（0正常 1停用）
    private String status;
//删除标志（0代表存在 2代表删除）
    private String delFlag;
}

