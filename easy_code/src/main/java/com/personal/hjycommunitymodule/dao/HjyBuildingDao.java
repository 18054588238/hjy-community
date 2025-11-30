package com.personal.hjycommunitymodule.dao;

dao;

import .entity.HjyBuilding;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * 楼栋 (HjyBuilding)表数据库访问层
 *
 * @author makejava
 * @since 2025-12-01 07:41:47
 */
public interface HjyBuildingDao {

    /**
     * 通过ID查询单条数据
     *
     * @param  主键
     * @return 实例对象
     */
    HjyBuilding queryById( );

    /**
     * 查询指定行数据
     *
     * @param hjyBuilding 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<HjyBuilding> queryAllByLimit(HjyBuilding hjyBuilding, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param hjyBuilding 查询条件
     * @return 总行数
     */
    long count(HjyBuilding hjyBuilding);

    /**
     * 新增数据
     *
     * @param hjyBuilding 实例对象
     * @return 影响行数
     */
    int insert(HjyBuilding hjyBuilding);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<HjyBuilding> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<HjyBuilding> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<HjyBuilding> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<HjyBuilding> entities);

    /**
     * 修改数据
     *
     * @param hjyBuilding 实例对象
     * @return 影响行数
     */
    int update(HjyBuilding hjyBuilding);

    /**
     * 通过主键删除数据
     *
     * @param  主键
     * @return 影响行数
     */
    int deleteById( );

}

