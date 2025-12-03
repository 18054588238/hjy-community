package com.personal.hjycommunitymodule.web.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.personal.hjycommunitymodule.common.constant.HttpStatus;
import com.personal.hjycommunitymodule.common.core.page.PageResult;
import com.personal.hjycommunitymodule.common.utils.ServletUtils;
import com.personal.hjycommunitymodule.community.domain.HjyCommunity;
import com.personal.hjycommunitymodule.community.domain.dto.HjyCommunityDto;
import com.personal.hjycommunitymodule.community.service.HjyCommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName HjyCommunityController
 * @Author liupanpan
 * @Date 2025/12/2
 * @Description
 */
@RestController
@RequestMapping("/community")
public class HjyCommunityController extends BaseController {
    @Autowired
    private HjyCommunityService hjyCommunityService;

    // 获取小区信息列表
    @GetMapping("/list")
    public PageResult getHjyCommunityList(HjyCommunity hjyCommunity){
        startPage();

        List<HjyCommunityDto> dtos = hjyCommunityService.queryList(hjyCommunity);

        return getPageResult(dtos);
    }

    // 新增小区信息

}
