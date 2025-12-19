package com.personal.hjycommunitymodule.community.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName MenuVo
 * @Author liupanpan
 * @Date 2025/12/19
 * @Description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuVo {
    /** * 路由名字 */
    private String name;
    /** * 路由地址 */
    private String path;
    /** * 是否隐藏路由，当设置 true 的时候该路由不会再侧边栏出现 */
    private boolean hidden;
    /** * 重定向地址，当设置 noRedirect 的时候该路由在面包屑导航中不可被点击 */
    private String redirect;
    /** * 组件地址 */
    private String component;
    /** * 当你一个路由下面的 children 声明的路由大于1个时，自动会变成嵌套的模式--如组件页面 */
    private Boolean alwaysShow; /** * 其他元素 */
    private MetaVo meta;
    /** * 子路由 */
    private List<MenuVo> children;
}
