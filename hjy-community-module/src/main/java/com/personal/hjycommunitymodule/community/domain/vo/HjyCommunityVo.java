package com.personal.hjycommunitymodule.community.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName HjyCommunityVo
 * @Author liupanpan
 * @Date 2025/12/7
 * @Description
 */
@Data
public class HjyCommunityVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long communityId;

    private String communityName;
}
