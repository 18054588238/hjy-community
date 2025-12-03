package com.personal.hjycommunitymodule.web.controller;

import com.personal.hjycommunitymodule.common.core.domain.BaseResponse;
import com.personal.hjycommunitymodule.community.domain.dto.SysAreaDto;
import com.personal.hjycommunitymodule.community.service.SysAreaService;
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
@RequestMapping("/sys/area")
public class SysAreaController {

    @Autowired
    private SysAreaService sysAreaService;

    @GetMapping("/getAreaTree")
    public BaseResponse getAreaTree() {
        List<SysAreaDto> areaTree = sysAreaService.getAreaTree();
        return BaseResponse.success(areaTree);
    }

}
