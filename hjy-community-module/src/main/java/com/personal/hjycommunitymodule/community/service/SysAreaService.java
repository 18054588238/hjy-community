package com.personal.hjycommunitymodule.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.personal.hjycommunitymodule.community.domain.SysArea;
import com.personal.hjycommunitymodule.community.domain.dto.SysAreaDto;

import java.util.List;

/**
 * @ClassName SysAreaService
 * @Author liupanpan
 * @Date 2025/12/3
 * @Description
 */
public interface SysAreaService extends IService<SysArea> {
    // 查询区域的树形结构
    List<SysAreaDto> getAreaTree();
}
