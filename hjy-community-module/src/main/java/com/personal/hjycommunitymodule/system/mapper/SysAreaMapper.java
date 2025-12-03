package com.personal.hjycommunitymodule.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.personal.hjycommunitymodule.system.domain.SysArea;

import java.util.List;

/**
 * @ClassName SysAreaMapper
 * @Author liupanpan
 * @Date 2025/12/3
 * @Description
 */
public interface SysAreaMapper extends BaseMapper<SysArea> {

    List<SysArea> getSysAreaList();
}
