package com.personal.hjycommunitymodule.web.controller;

import com.personal.hjycommunitymodule.common.core.controller.BaseController;
import com.personal.hjycommunitymodule.common.core.domain.BaseResponse;
import com.personal.hjycommunitymodule.system.domain.SysDept;
import com.personal.hjycommunitymodule.system.service.SysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName SysDeptController
 * @Author liupanpan
 * @Date 2025/12/4
 * @Description
 */
@RestController
@RequestMapping("/system/dept")
public class SysDeptController extends BaseController {

    @Autowired
    private SysDeptService sysDeptService;

    // 更换小区物业 - 获取物业公司信息
    @GetMapping("/list")
    public BaseResponse list(SysDept sysDept) {
        // 参数sysDept暂时未用到，后面可能会用到，到时候再补充
        List<SysDept> list = sysDeptService.getDeptList(sysDept);
        return BaseResponse.success(list);
    }

}
