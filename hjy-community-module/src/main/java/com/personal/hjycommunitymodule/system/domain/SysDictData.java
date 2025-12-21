package com.personal.hjycommunitymodule.system.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableId;
import com.personal.hjycommunitymodule.common.constant.UserConstants;
import com.personal.hjycommunitymodule.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 字典数据表 对应实体
 * @author spikeCong
 * @date 2023/5/22
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class SysDictData extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 字典编码 */
    @Excel(name = "字典编码")
    @TableId
    private Long dictCode;

    /** 字典排序 */
    @Excel(name = "字典排序")
    private Long dictSort;

    /** 字典标签 */
    @Excel(name = "字典标签")
    @NotBlank(message = "字典标签不能为空")
    @Size(min = 0, max = 100, message = "字典标签长度不能超过100个字符")
    private String dictLabel;

    /** 字典键值 */
    @Excel(name = "字典键值")
    @NotBlank(message = "字典键值不能为空")
    @Size(min = 0, max = 100, message = "字典键值长度不能超过100个字符")
    private String dictValue;

    /** 字典类型 */
    @Excel(name = "字典类型")
    @NotBlank(message = "字典类型不能为空")
    @Size(min = 0, max = 100, message = "字典类型长度不能超过100个字符")
    private String dictType;

    /** 样式属性（其他样式扩展） */
    @Size(min = 0, max = 100, message = "样式属性长度不能超过100个字符")
    private String cssClass;

    /** 表格字典样式 */
    private String listClass;

    /** 是否默认（Y是 N否） */
    @Excel(name = "是否默认",replace = {"是_Y","否_N"})
    private String isDefault;

    /** 状态（0正常 1停用） */
    @Excel(name = "状态", replace = {"正常_0","停用_1"})
    private String status;


    public boolean getDefault()
    {
        return UserConstants.YES.equals(this.isDefault);
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("dictCode", getDictCode())
                .append("dictSort", getDictSort())
                .append("dictLabel", getDictLabel())
                .append("dictValue", getDictValue())
                .append("dictType", getDictType())
                .append("cssClass", getCssClass())
                .append("listClass", getListClass())
                .append("isDefault", getIsDefault())
                .append("status", getStatus())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .toString();
    }

}

