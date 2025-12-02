package com.personal.hjycommunitymodule.common.core.page;

import lombok.Data;

/**
 * @ClassName PageDomain
 * @Author liupanpan
 * @Date 2025/12/2
 * @Description 封装分页请求对象
 */
@Data
public class PageDomain {
    /* 当前记录起始索引 */
    private Integer pageNum;
    /* 每页显示记录数 */
    private Integer pageSize;
}
