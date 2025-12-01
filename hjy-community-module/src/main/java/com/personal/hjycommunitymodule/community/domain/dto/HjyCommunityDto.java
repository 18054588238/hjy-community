package com.personal.hjycommunitymodule.community.domain.dto;

import com.personal.hjycommunitymodule.community.domain.HjyCommunity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName HjyCommunityDto
 * @Author liupanpan
 * @Date 2025/12/1
 * @Description
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class HjyCommunityDto extends HjyCommunity {

    private String communityProvenceName;

    private String communityCityName;

    private String communityTownName;

}
