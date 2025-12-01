package com.personal.hjycommunitymodule.community.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.personal.hjycommunitymodule.community.domain.HjyCommunity;
import com.personal.hjycommunitymodule.community.domain.dto.HjyCommunityDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @ClassName HjyCommunityMapper
 * @Author liupanpan
 * @Date 2025/12/1
 * @Description
 */
@Mapper
public interface HjyCommunityMapper extends BaseMapper<HjyCommunity> {
    // 带条件查询小区信息
    @Select("<script>\n" +
            "SELECT \n" +
            " *,\n" +
            " s1.`name` AS communityProvinceName,\n" +
            " s2.`name` AS communityCityName,\n" +
            " s3.`name` AS communityTownName\n" +
            "FROM hjy_community hc \n" +
            "LEFT JOIN sys_area s1 ON hc.`community_province_code` = s1.`code`\n" +
            "LEFT JOIN sys_area s2 ON hc.`community_city_code` = s2.`code`\n" +
            "LEFT JOIN sys_area s3 ON hc.`community_town_code` = s3.`code`\n" +
            "<where>\n" +
            "    <if test=\"communityName !=null and communityName != ''\">\n" +
            "        hc.community_name like concat('%',#{communityName},'%')\n" +
            "    </if> \n" +
            "    <if test=\"communityCode !=null and communityCode != ''\">\n" +
            "        and hc.community_code = #{communityCode}\n" +
            "    </if> \n" +
            "</where>\n" +
            "</script>")
    /*在MyBatis的注解中，使用<script>标签来包裹动态SQL，这可以在SQL中使用MyBatis的动态SQL标签，如<where>、<if>等。*/
    List<HjyCommunityDto> queryList(HjyCommunity hjyCommunity);
}
