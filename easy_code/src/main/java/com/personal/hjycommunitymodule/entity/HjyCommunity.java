package com.personal.hjycommunitymodule.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * 小区 (HjyCommunity)实体类
 *
 * @author makejava
 * @since 2025-12-01 07:41:55
 */
public class HjyCommunity implements Serializable {
    private static final long serialVersionUID = -20409287249578148L;
/**
     * 小区id
     */
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
    private Long deptId;
/**
     * 排序
     */
    private Integer communitySort;
/**
     * 创建者
     */
    private String createBy;
/**
     * 创建时间
     */
    private LocalDateTime createTime;
/**
     * 更新者
     */
    private String updateBy;
/**
     * 更新时间
     */
    private LocalDateTime updateTime;
/**
     * 备注
     */
    private String remark;


    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public String getCommunityCode() {
        return communityCode;
    }

    public void setCommunityCode(String communityCode) {
        this.communityCode = communityCode;
    }

    public String getCommunityProvinceCode() {
        return communityProvinceCode;
    }

    public void setCommunityProvinceCode(String communityProvinceCode) {
        this.communityProvinceCode = communityProvinceCode;
    }

    public String getCommunityCityCode() {
        return communityCityCode;
    }

    public void setCommunityCityCode(String communityCityCode) {
        this.communityCityCode = communityCityCode;
    }

    public String getCommunityTownCode() {
        return communityTownCode;
    }

    public void setCommunityTownCode(String communityTownCode) {
        this.communityTownCode = communityTownCode;
    }

    public String getCommunityDetailedAddress() {
        return communityDetailedAddress;
    }

    public void setCommunityDetailedAddress(String communityDetailedAddress) {
        this.communityDetailedAddress = communityDetailedAddress;
    }

    public String getCommunityLongitude() {
        return communityLongitude;
    }

    public void setCommunityLongitude(String communityLongitude) {
        this.communityLongitude = communityLongitude;
    }

    public String getCommunityLatitude() {
        return communityLatitude;
    }

    public void setCommunityLatitude(String communityLatitude) {
        this.communityLatitude = communityLatitude;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Integer getCommunitySort() {
        return communitySort;
    }

    public void setCommunitySort(Integer communitySort) {
        this.communitySort = communitySort;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}

