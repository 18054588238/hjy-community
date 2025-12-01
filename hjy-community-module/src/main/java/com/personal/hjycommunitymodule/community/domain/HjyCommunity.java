package com.personal.hjycommunitymodule.community.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.personal.hjycommunitymodule.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 小区 (HjyCommunity)实体类
 *
 * @author makejava
 * @since 2025-12-01 07:41:55
 */
// callSuper = true 表示在生成的equals() 和 hashCode() 方法中会包含父类的字段。
@EqualsAndHashCode(callSuper = true)
@Data
public class HjyCommunity extends BaseEntity {

    private static final long serialVersionUID = -20409287249578148L;
/**
     * 小区id
     */
    @TableId
    /*默认情况下，数字、布尔值等会以 JSON 原始类型输出。使用此注解后，它们会被转换为字符串。能够避免精度问题
    * */
    // 表示将属性序列化为字符串（即使原本是数字或其他类型）。在反序列化时，期望接收一个字符串，但Jackson会尝试将其转换为Long类型
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long communityId;
/**
     * 小区名称
     */
    private String communityName;
/**
     * 小区编码
     */
    private String communityCode;
/**
     * 省区划码
     */
    private String communityProvinceCode;
/**
     * 市区划码
     */
    private String communityCityCode;
/**
     * 区县区划码
     */
    private String communityTownCode;
/**
     * 详细地址
     */
    private String communityDetailedAddress;
/**
     * 经度
     */
    private String communityLongitude;
/**
     * 纬度
     */
    private String communityLatitude;
/**
     * 物业id
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long deptId;
/**
     * 排序
     */
    private Integer communitySort;

}

