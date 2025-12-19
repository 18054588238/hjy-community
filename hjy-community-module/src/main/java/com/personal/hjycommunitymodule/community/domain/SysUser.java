package com.personal.hjycommunitymodule.community.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.personal.hjycommunitymodule.common.core.domain.BaseEntity;
import com.personal.hjycommunitymodule.system.domain.SysDept;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.minidev.json.annotate.JsonIgnore;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

/**
 * 用户信息表
 * @TableName sys_user
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value ="sys_user")
public class SysUser extends BaseEntity {
    /**
     * 用户ID
     */
    @TableId
    private Long userId;

    /**
     * 部门ID
     */
    private Long deptId;

    /**
     * 用户账号
     */
    @NotBlank(message = "账号不能为空")
    @Size(min = 0,max = 30,message = "用户账号不能超过30个字符")
    private String userName;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 用户类型（00系统用户）
     */
    private String userType;

    /**
     * 用户邮箱
     */
    @Email(message = "邮箱格式不正确")
    @Size(min = 0,max = 50,message = "邮箱不能超过30个字符")
    private String email;

    /**
     * 手机号码
     */
    @Size(min = 0,max = 11,message = "手机号码不能超过11个字符")
    private String phonenumber;

    /**
     * 用户性别（0男 1女 2未知）
     */
    private String sex;

    /**
     * 头像地址
     */
    private String avatar;

    /**
     * 密码
     */
    @JsonIgnore // 序列化时忽略密码
    private String password;

    /** 盐加密 */
    @TableField(exist = false)
    private String salt;

    /**
     * 帐号状态（0正常 1停用）
     */
    private String status;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private String delFlag;

    /**
     * 最后登录IP
     */
    private String loginIp;

    /**
     * 最后登录时间
     */
    private Date loginDate;

    @TableField(exist = false)
    private SysDept dept;

    @TableField(exist = false)
    private List<SysRole> roles;
    @TableField(exist = false)
    private List<Long> roleIds;
    /** 岗位组 */
    @TableField(exist = false)
    private List<Long> postIds;

    //判断是否是admin
    public boolean isAdmin() {
        return isAdmin(this.userId);
    }
    public static boolean isAdmin(Long userId) {
        return userId != null && 1L == userId;
    }

}