package com.personal.hjycommunitymodule.web.controller;

import com.personal.hjycommunitymodule.common.core.controller.BaseController;
import com.personal.hjycommunitymodule.common.core.domain.BaseResponse;
import com.personal.hjycommunitymodule.common.core.page.PageResult;
import com.personal.hjycommunitymodule.common.utils.SecurityUtils;
import com.personal.hjycommunitymodule.system.domain.SysDictData;
import com.personal.hjycommunitymodule.system.service.SysDictDataService;
import com.personal.hjycommunitymodule.system.service.SysDictTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName SysDictController
 * @Author liupanpan
 * @Date 2025/12/21
 * @Description
 */

@RestController
@RequestMapping("/system/dict/data")
public class SysDictController extends BaseController {

    @Autowired
    private SysDictTypeService dictTypeService;

    @Autowired
    private SysDictDataService dictDataService;

    /**
     * 查询字典数据列表 - 分页
     */
    @RequestMapping("/list")
    public PageResult list(SysDictData sysDictData){
        startPage(); // 开启分页
        List<SysDictData> list = dictDataService.selectDictDataList(sysDictData);
        return getPageResult(list);
    }

    /**
     * 根据Id查询字典详细信息
     */
    @GetMapping(value = "/{dictCode}")
    public BaseResponse getInfo(@PathVariable Long dictCode){

        return BaseResponse.success(dictDataService.selectDictDataById(dictCode));
    }

    /**
     * 根据字典类型查询字典数据信息
     */
    @GetMapping(value = "/type/{dictType}")
    public BaseResponse getInfo(@PathVariable String dictType){

        return BaseResponse.success(dictTypeService.selectDictDataByType(dictType));
    }

    /**
     * 新增字典数据
     */
    @PostMapping
    public BaseResponse add(@RequestBody SysDictData sysDictData){
        // 获取当前登录用户
        sysDictData.setCreateBy(SecurityUtils.getCurUser().getUsername());
        return getBaseResponse(dictDataService.insertDictData(sysDictData));
    }

    /**
     * 修改字典数据
     */
    @PutMapping
    public BaseResponse edit(@RequestBody SysDictData sysDictData){
        sysDictData.setUpdateBy(SecurityUtils.getCurUser().getUsername());
        return getBaseResponse(dictDataService.updateDictData(sysDictData));
    }

    /**
     *删除字典数据
     */
    @DeleteMapping("/{dictCodes}")
    public BaseResponse remove(@PathVariable Long[] dictCodes){

        return getBaseResponse(dictDataService.deleteDictDataByIds(dictCodes));
    }
}
