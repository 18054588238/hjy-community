package com.personal.hjycommunitymodule.web.controller;

import com.personal.hjycommunitymodule.common.core.domain.BaseResponse;
import com.personal.hjycommunitymodule.system.domain.dto.SysAreaDto;
import com.personal.hjycommunitymodule.system.service.SysAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName SysAreaController
 * @Author liupanpan
 * @Date 2025/12/3
 * @Description
 */
@RestController
@RequestMapping("/system/area")
public class SysAreaController {

    @Autowired
    private SysAreaService sysAreaService;

    @GetMapping("/tree")
    public BaseResponse getAreaTree() {
        List<SysAreaDto> areaTree = sysAreaService.getAreaTree();
        return BaseResponse.success(areaTree);
    }

}
