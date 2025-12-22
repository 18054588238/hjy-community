package com.personal.hjycommunitymodule.web.controller;

import com.personal.hjycommunitymodule.common.constant.UserConstants;
import com.personal.hjycommunitymodule.common.core.controller.BaseController;
import com.personal.hjycommunitymodule.common.core.domain.BaseResponse;
import com.personal.hjycommunitymodule.common.core.page.PageResult;
import com.personal.hjycommunitymodule.common.utils.SecurityUtils;
import com.personal.hjycommunitymodule.system.domain.SysDictType;
import com.personal.hjycommunitymodule.system.service.SysDictTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName SysDictTypeController
 * @Author liupanpan
 * @Date 2025/12/21
 * @Description
 */
@RestController
@RequestMapping("/system/dict/type")
public class SysDictTypeController extends BaseController {

    @Autowired
    private SysDictTypeService sysDictTypeService;

    /**
     * 多条件分页查询字典类型数据
     */
    @GetMapping("/list")
    public PageResult list(SysDictType dictType){
        startPage();
        List<SysDictType> list = sysDictTypeService.selectDictTypeList(dictType);
        return getPageResult(list);
    }

    /**
     * 根据Id查询字典类型详细信息
     */
    @GetMapping(value = "/{dictId}")
    public BaseResponse getInfo(@PathVariable Long dictId){
        return BaseResponse.success(sysDictTypeService.selectDictTypeById(dictId));
    }

    /*
     * 新增字典类型
     */
    @PostMapping
    public BaseResponse add(@RequestBody SysDictType sysDictType){

        // 新增字典类型时，判断是否已存在
        if(UserConstants.NOT_UNIQUE.equals(sysDictTypeService.checkDictTypeUnique(sysDictType))){
            return BaseResponse.fail("新增字典" + sysDictType.getDictName() + "失败,字典类型已经存在");
        }
        sysDictType.setCreateBy(SecurityUtils.getCurUser().getUsername());
        return getBaseResponse(sysDictTypeService.insertDictType(sysDictType));
    }

    /*
     * 修改字典类型
     */
    @PutMapping
    public BaseResponse edit(@RequestBody SysDictType sysDictType){
        // 判断更新的字典类型是否已经存在
        if(UserConstants.NOT_UNIQUE.equals(sysDictTypeService.checkDictTypeUnique(sysDictType))){
            return BaseResponse.fail("修改字典" + sysDictType.getDictName() + "失败,字典类型已经存在");
        }
        sysDictType.setUpdateBy(SecurityUtils.getCurUser().getUsername());
        return getBaseResponse(sysDictTypeService.updateDictType(sysDictType));
    }

    /*
     * 删除字典类型
     */
    @DeleteMapping("/{dictIds}")
    public BaseResponse remove(@PathVariable Long[] dictIds){
        return getBaseResponse(sysDictTypeService.deleteDictTypeByIds(dictIds));
    }

    /**
     * 清空缓存
     */
    @DeleteMapping("/clearCache")
    public BaseResponse clearCache(){
        sysDictTypeService.clearCache();
        return BaseResponse.success("清除缓存成功");
    }
}
