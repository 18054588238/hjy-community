package com.personal.hjycommunitymodule.dao;

dao;

import .entity.HjyCommunity;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * 小区 (HjyCommunity)表数据库访问层
 *
 * @author makejava
 * @since 2025-12-01 07:41:55
 */
public interface HjyCommunityDao {

    /**
     * 通过ID查询单条数据
     *
     * @param communityId 主键
     * @return 实例对象
     */
    HjyCommunity queryById(Long communityId);

    /**
     * 查询指定行数据
     *
     * @param hjyCommunity 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<HjyCommunity> queryAllByLimit(HjyCommunity hjyCommunity, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param hjyCommunity 查询条件
     * @return 总行数
     */
    long count(HjyCommunity hjyCommunity);

    /**
     * 新增数据
     *
     * @param hjyCommunity 实例对象
     * @return 影响行数
     */
    int insert(HjyCommunity hjyCommunity);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<HjyCommunity> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<HjyCommunity> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<HjyCommunity> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<HjyCommunity> entities);

    /**
     * 修改数据
     *
     * @param hjyCommunity 实例对象
     * @return 影响行数
     */
    int update(HjyCommunity hjyCommunity);

    /**
     * 通过主键删除数据
     *
     * @param communityId 主键
     * @return 影响行数
     */
    int deleteById(Long communityId);

}

