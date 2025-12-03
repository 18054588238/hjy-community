package com.personal.hjycommunitymodule.web.controller;

import com.personal.hjycommunitymodule.common.core.controller.BaseController;
import com.personal.hjycommunitymodule.common.core.domain.BaseResponse;
import com.personal.hjycommunitymodule.common.core.page.PageResult;
import com.personal.hjycommunitymodule.community.domain.HjyCommunity;
import com.personal.hjycommunitymodule.community.domain.dto.HjyCommunityDto;
import com.personal.hjycommunitymodule.community.service.HjyCommunityService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/add")
    public BaseResponse add(@RequestBody HjyCommunity hjyCommunity){
        return getBaseResponse(hjyCommunityService.addHjyCommunity(hjyCommunity));
    }

    // 修改小区信息 - 回显数据
    @GetMapping("/findById/{communityId}")
    public BaseResponse findById(@PathVariable Long communityId){
        return BaseResponse.success(hjyCommunityService.findById(communityId));
    }

    // 修改小区信息
    @PutMapping("/update")
    public BaseResponse updateCommunity(@RequestBody HjyCommunity hjyCommunity){
        return getBaseResponse(hjyCommunityService.updateCommunity(hjyCommunity));
    }

    // 删除小区信息
    @DeleteMapping("/delete/{communityIds}")
    public BaseResponse delete(@PathVariable List<Long> communityIds){
        return getBaseResponse(hjyCommunityService.deleteCommunity(communityIds));
    }

}
