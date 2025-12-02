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
public class HjyCommunityController {
    @Autowired
    private HjyCommunityService hjyCommunityService;

    @GetMapping("/list")
    public PageResult getHjyCommunityList(HjyCommunity hjyCommunity){
        // 获取分页参数
        Integer pageNum = ServletUtils.getParameterToInt("pageNum");
        Integer pageSize = ServletUtils.getParameterToInt("pageSize");
        PageHelper.startPage(pageNum, pageSize);// 会自动执行分页操作

        List<HjyCommunityDto> dtos = hjyCommunityService.queryList(hjyCommunity);

        PageResult pageResult = new PageResult();
        pageResult.setCode(HttpStatus.SUCCESS);
        pageResult.setMsg("查询成功");
        pageResult.setRows(dtos);
        pageResult.setTotal(new PageInfo(dtos).getTotal()); // 获取总条数

        return pageResult;
    }
}
