package com.personal.hjycommunitymodule.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.personal.hjycommunitymodule.system.domain.SysArea;
import com.personal.hjycommunitymodule.system.domain.dto.SysAreaDto;
import com.personal.hjycommunitymodule.system.mapper.SysAreaMapper;
import com.personal.hjycommunitymodule.system.service.SysAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName SysAreaServiceImpl
 * @Author liupanpan
 * @Date 2025/12/3
 * @Description
 */
@Service
public class SysAreaServiceImpl extends ServiceImpl<SysAreaMapper, SysArea> implements SysAreaService {

    @Autowired
    private SysAreaMapper sysAreaMapper;

    @Override
    public List<SysAreaDto> getAreaTree() {
        List<SysArea> sysAreaList = sysAreaMapper.getSysAreaList();

        return sysAreaList.stream()
                .filter(i -> i.getParentCode() == 0)
                .map(i -> SysAreaDto.builder()
                        .code(i.getCode())
                        .name(i.getName())
                        .children(getChildren(i.getCode(), sysAreaList))
                        .build()).collect(Collectors.toList());
    }

    private List<SysAreaDto> getChildren(Integer code,List<SysArea> sysAreaList) {
        List<SysArea> sysAreas = sysAreaList.stream()
                .filter(i -> i.getParentCode().equals(code)).collect(Collectors.toList());
        if (sysAreas == null || sysAreas.isEmpty()) {
            return null;
        }
        return sysAreaList.stream()
                .filter(i->i.getParentCode().equals(code))
                .map(i->
                    SysAreaDto.builder()
                            .code(i.getCode())
                            .name(i.getName())
                            .children(getChildren(i.getCode(),sysAreaList))
                            .build()).collect(Collectors.toList());
    }
}
