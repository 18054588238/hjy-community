package com.personal.hjycommunitymodule.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.personal.hjycommunitymodule.community.domain.HjyCommunity;
import com.personal.hjycommunitymodule.community.domain.dto.HjyCommunityDto;
import com.personal.hjycommunitymodule.community.domain.vo.HjyCommunityVo;

import java.util.List;

/**
 * @ClassName HjyCommunityService
 * @Author liupanpan
 * @Date 2025/12/1
 * @Description
 */
public interface HjyCommunityService extends IService<HjyCommunity> {
    // 带条件查询小区信息
    List<HjyCommunityDto> queryList(HjyCommunity hjyCommunity);
    // 新增
    Integer addHjyCommunity(HjyCommunity hjyCommunity);

    HjyCommunity findById(Long communityId);

    Integer updateCommunity(HjyCommunity hjyCommunity);

    Integer deleteCommunity(List<Long> communityIds);

    List<HjyCommunityVo> queryPullDown();
}
