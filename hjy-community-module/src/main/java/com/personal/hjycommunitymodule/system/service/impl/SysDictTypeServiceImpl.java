package com.personal.hjycommunitymodule.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.personal.hjycommunitymodule.common.constant.Constants;
import com.personal.hjycommunitymodule.common.constant.UserConstants;
import com.personal.hjycommunitymodule.common.core.exception.CustomException;
import com.personal.hjycommunitymodule.common.utils.RedisCache;
import com.personal.hjycommunitymodule.system.domain.SysDictData;
import com.personal.hjycommunitymodule.system.domain.SysDictType;
import com.personal.hjycommunitymodule.system.mapper.SysDictDataMapper;
import com.personal.hjycommunitymodule.system.mapper.SysDictTypeMapper;
import com.personal.hjycommunitymodule.system.service.SysDictTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author spikeCong
 * @date 2023/5/19
 **/
@Service
public class SysDictTypeServiceImpl implements SysDictTypeService {

    @Autowired
    private SysDictTypeMapper dictTypeMapper;

    @Autowired
    private SysDictDataMapper dictDataMapper;

    @Autowired
    private RedisCache redisCache;

    /** 缓存预热
     * 项目启动时 初始化字典数据到缓存 - 根据字典类型缓存数据
     */
    @PostConstruct // 标记一个方法在 Bean 初始化完成、依赖注入之后，但在 Bean 投入使用之前 被执行
    public void init(){

        LambdaQueryWrapper<SysDictData> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysDictData::getStatus,"0");
        Map<String, List<SysDictData>> dictMap = dictDataMapper.selectList(wrapper).stream()
                .collect(Collectors.groupingBy(SysDictData::getDictType));

        dictMap.forEach((dictType,dictDataList)->{
            redisCache.setCacheList(dictType,dictDataList);
        });

    }

    private String getCacheKey(String type){
        return Constants.SYS_DICT_KEY + type;
    }

    @Override
    public List<SysDictType> selectDictTypeList(SysDictType dictType) {

        return dictTypeMapper.selectDictTypeList(dictType);
    }

    @Override
    public List<SysDictType> selectDictTypeAll() {
        return dictTypeMapper.selectDictTypeAll();
    }

    /*
     * 根据字典类型查询字典数据
     */
    @Override
    public List<SysDictData> selectDictDataByType(String dictType) {

        //先从缓存获取
        List<SysDictData> dictData = redisCache.getCacheObject(getCacheKey(dictType));
        if(!Objects.isNull(dictData)){
            return dictData;
        }

        //缓存没有,查询数据库
        dictData = dictDataMapper.selectDictDataByType(dictType);
        if(!Objects.isNull(dictData)){
            redisCache.setCacheObject(getCacheKey(dictType),dictData);
            return dictData;
        }

        return null;
    }

    @Override
    public SysDictType selectDictTypeById(Long dictId) {
        return dictTypeMapper.selectDictTypeById(dictId);
    }

    /**
     * 根据字典类型查询字典数据
     */
    @Override
    public SysDictType selectDictTypeByType(String dictType) {
        return dictTypeMapper.selectDictTypeByType(dictType);
    }

    /**
     * 批量删除
     */
    @Override
    public int deleteDictTypeByIds(Long[] dictIds) {
        for (Long dictId : dictIds) {
            SysDictType sysDictType = selectDictTypeById(dictId);
            //判断该类型下是否有对应的字典数据
            if(dictDataMapper.countDictDataByType(sysDictType.getDictType()) > 0){
                throw new CustomException(500,"已分配不能删除");
            }
        }

        int row = dictTypeMapper.deleteDictTypeByIds(dictIds);
        //删除成功,清除缓存
        if(row > 0){
            clear();
        }
        return row;
    }

    //清空缓存
    private void clear(){
        Collection<String> keys = redisCache.keys(Constants.SYS_DICT_KEY + "*");
        redisCache.deleteObject(keys);
    }

    /**
     * 新增
     */
    @Override
    public int insertDictType(SysDictType dictType) {
        int row = dictTypeMapper.insertDictType(dictType);

        if(row > 0){
            clear();
        }
        return row;
    }

    /**
     * 修改字典类型信息
     * @param dictType
     * @return: int
     */
    @Override
    @Transactional //执行多条SQL 添加事务注解
    public int updateDictType(SysDictType dictType) {

        //修改字典数据表的字典类型
        SysDictType oldDict = dictTypeMapper.selectDictTypeById(dictType.getDictId());
        dictDataMapper.updateDictDataType(oldDict.getDictType(),dictType.getDictType());

        //修改字典类型表
        int row = dictTypeMapper.updateDictType(dictType);
        if(row > 0){
            clear();
        }
        return row;
    }

    /**
     * 校验字典类型是否唯一
     */
    @Override
    public String checkDictTypeUnique(SysDictType dictType) {

        SysDictType sysDictType = dictTypeMapper.checkDictTypeUnique(dictType.getDictType());
        if(!Objects.isNull(sysDictType)){
            return UserConstants.NOT_UNIQUE;
        }

        return UserConstants.UNIQUE;
    }

    @Override
    public void clearCache() {
        clear();
    }

}
