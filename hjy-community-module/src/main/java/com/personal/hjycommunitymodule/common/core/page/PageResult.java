package com.personal.hjycommunitymodule.common.core.page;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName PageResult
 * @Author liupanpan
 * @Date 2025/12/2
 * @Description 分页查询统一封装响应对象
 */
@Data
@NoArgsConstructor
public class PageResult implements Serializable {

    private static final long serialVersionUID = -7932162198339560854L;

    /*总记录数*/
    private Long total;
    /*列表数据*/
    private List<?> rows;
    /*消息状态码*/
    private Integer code;
    /*消息内容*/
    private String msg;

    public PageResult(Long total, List<?> rows) {
        this.total = total;
        this.rows = rows;
    }
}
