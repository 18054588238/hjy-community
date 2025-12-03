package com.personal.hjycommunitymodule.common.core.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.personal.hjycommunitymodule.common.constant.HttpStatus;
import com.personal.hjycommunitymodule.common.core.domain.BaseResponse;
import com.personal.hjycommunitymodule.common.core.page.PageDomain;
import com.personal.hjycommunitymodule.common.core.page.PageResult;
import com.personal.hjycommunitymodule.common.utils.ServletUtils;

import java.util.List;

/**
 * @ClassName BaseController
 * @Author liupanpan
 * @Date 2025/12/3
 * @Description Controller基类，提供一些公用方法
 */
public class BaseController {

    /*处理分页查询*/
    private static final String PAGE_NUM = "pageNum";
    private static final String PAGE_SIZE = "pageSize";
    // 封装分页请求对象
    public static PageDomain getPageDomain() {
        // 获取分页参数
        Integer pageNum = ServletUtils.getParameterToInt(PAGE_NUM);
        Integer pageSize = ServletUtils.getParameterToInt(PAGE_SIZE);
        return new PageDomain(pageNum, pageSize);
    }
    // 调用PageHelper的startPage，设置分页参数
    protected void startPage() {
        PageDomain pageDomain = getPageDomain();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        if ( pageNum != null && pageSize != null ) {
            PageHelper.startPage(pageNum,pageSize);// 会自动执行分页操作
        }
    }
    // 响应分页数据
    protected PageResult getPageResult(List<?> rows) {
        PageResult pageResult = new PageResult();
        pageResult.setCode(HttpStatus.SUCCESS);
        pageResult.setMsg("查询成功");
        pageResult.setRows(rows);
        pageResult.setTotal(new PageInfo(rows).getTotal()); // 获取总条数

        return pageResult;
    }

    // 封装响应信息
    public BaseResponse getBaseResponse(Integer rows) {
        if (rows > 0) {
            return BaseResponse.success(rows);
        }else {
            return BaseResponse.fail("操作失败");
        }
    }

}
