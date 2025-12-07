package com.personal.hjycommunitymodule.community.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.personal.hjycommunitymodule.common.utils.OrikaUtils;
import com.personal.hjycommunitymodule.community.domain.HjyCommunity;
import com.personal.hjycommunitymodule.community.domain.dto.HjyCommunityDto;
import com.personal.hjycommunitymodule.community.domain.vo.HjyCommunityVo;
import com.personal.hjycommunitymodule.community.mapper.HjyCommunityMapper;
import com.personal.hjycommunitymodule.community.service.HjyCommunityService;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName HjyCommunityServiceImpl
 * @Author liupanpan
 * @Date 2025/12/1
 * @Description
 */
@Service
public class HjyCommunityServiceImpl extends ServiceImpl<HjyCommunityMapper, HjyCommunity> implements HjyCommunityService {

    @Autowired
    private HjyCommunityMapper hjyCommunityMapper;

    private static final String CODE_PREFIX = "COMMUNITY_";

    @Override
    public List<HjyCommunityDto> queryList(HjyCommunity hjyCommunity) {

        return hjyCommunityMapper.queryList(hjyCommunity);
    }

    @Override
    public Integer addHjyCommunity(HjyCommunity hjyCommunity) {
        hjyCommunity.setCommunityCode(CODE_PREFIX + System.currentTimeMillis());
        return hjyCommunityMapper.insert(hjyCommunity);
    }

    @Override
    public HjyCommunity findById(Long communityId) {
        return hjyCommunityMapper.selectById(communityId);
    }

    @Override
    public Integer updateCommunity(HjyCommunity hjyCommunity) {
        return hjyCommunityMapper.updateById(hjyCommunity);
    }

    @Override
    public Integer deleteCommunity(List<Long> communityIds) {
        return hjyCommunityMapper.deleteBatchIds(communityIds);
    }

    @Override
    public List<HjyCommunityVo> queryPullDown() {
        List<HjyCommunity> hjyCommunities = hjyCommunityMapper.selectList(null);
        return hjyCommunities.stream().map(community ->
                OrikaUtils.convert(community, HjyCommunityVo.class))
                .collect(Collectors.toList());
    }
}
