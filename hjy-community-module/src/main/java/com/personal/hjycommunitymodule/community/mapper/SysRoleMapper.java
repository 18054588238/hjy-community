package com.personal.hjycommunitymodule.community.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.personal.hjycommunitymodule.community.domain.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author liupanpan
* @description 针对表【sys_role(角色信息表)】的数据库操作Mapper
* @createDate 2025-12-11 17:42:35
* @Entity generator.domain.SysRole
*/
public interface SysRoleMapper extends BaseMapper<SysRole> {

    List<SysRole> selectRolesByUserId(@Param("userId") Long userId);
}




