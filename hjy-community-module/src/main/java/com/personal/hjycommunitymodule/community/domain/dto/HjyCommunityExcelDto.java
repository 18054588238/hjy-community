package com.personal.hjycommunitymodule.community.domain.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import com.personal.hjycommunitymodule.community.domain.HjyCommunity;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @ClassName HjyCommunityExcelDto
 * @Author liupanpan
 * @Date 2025/12/4
 * @Description
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@ExcelTarget("community")
public class HjyCommunityExcelDto extends HjyCommunity {

    /** 小区id */
    @Excel(name = "序号")
    private Long communityId;
    /** 创建时间 */
    @Excel(name="创建时间",exportFormat = "yyyy年MM月dd日")
    private LocalDateTime createTime;
    /** 备注 */
    @Excel(name = "备注")
    private String remark;
    /**
     * 小区名称
     */
    @Excel(name = "小区名称")
    private String communityName;
    /**
     * 小区编码
     */
    @Excel(name = "小区编码")
    private String communityCode;
    /**
     * 详细地址
     */
    @Excel(name = "详细地址")
    private String communityDetailedAddress;

    @Excel(name = "省")
    private String communityProvinceName;

    @Excel(name = "市")
    private String communityCityName;
    @Excel(name = "区")
    private String communityTownName;
}
