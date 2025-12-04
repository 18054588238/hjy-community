package com.personal.hjycommunitymodule.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.personal.hjycommunitymodule.system.domain.SysDept;

import java.util.List;

/**
 * @ClassName SysDeptService
 * @Author liupanpan
 * @Date 2025/12/4
 * @Description
 */
public interface SysDeptService extends IService<SysDept> {
    List<SysDept> getDeptList(SysDept sysDept);
}
