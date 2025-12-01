package com.personal.hjycommunitymodule.community.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.personal.hjycommunitymodule.community.domain.HjyCommunity;
import com.personal.hjycommunitymodule.community.domain.dto.HjyCommunityDto;
import com.personal.hjycommunitymodule.community.mapper.HjyCommunityMapper;
import com.personal.hjycommunitymodule.community.service.HjyCommunityService;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<HjyCommunityDto> queryList(HjyCommunity hjyCommunity) {

        return hjyCommunityMapper.queryList(hjyCommunity);
    }
}
