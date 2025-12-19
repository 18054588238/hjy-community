package com.personal.hjycommunitymodule.community.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName MetaVo
 * @Author liupanpan
 * @Date 2025/12/19
 * @Description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MetaVo {
    /** * 设置该路由在侧边栏和面包屑中展示的名字 */
    private String title;
    /** * 设置该路由的图标，对应路径src/assets/icons/svg */
    private String icon;
    /** * 设置为true，则不会被 <keep-alive>缓存 */
    private boolean noCache;
}
