package com.personal.hjycommunitymodule.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.personal.hjycommunitymodule.system.domain.SysDept;
import com.personal.hjycommunitymodule.system.mapper.SysDeptMapper;
import com.personal.hjycommunitymodule.system.service.SysDeptService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @ClassName SysDeptServiceImpl
 * @Author liupanpan
 * @Date 2025/12/4
 * @Description
 */
@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {
    @Override
    public List<SysDept> getDeptList(SysDept sysDept) {

        QueryWrapper<SysDept> wrapper = new QueryWrapper<>();
        wrapper.eq("del_flag","0")
                .orderByAsc("parent_id","order_num");
        return this.list(wrapper);
    }
}
