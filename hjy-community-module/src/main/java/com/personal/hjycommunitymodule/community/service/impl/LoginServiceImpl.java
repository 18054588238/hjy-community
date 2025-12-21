package com.personal.hjycommunitymodule.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.personal.hjycommunitymodule.common.constant.Constants;
import com.personal.hjycommunitymodule.common.constant.UserConstants;
import com.personal.hjycommunitymodule.common.core.domain.BaseResponse;
import com.personal.hjycommunitymodule.common.core.exception.CustomException;
import com.personal.hjycommunitymodule.common.utils.ChainedMap;
import com.personal.hjycommunitymodule.common.utils.JWTUtils;
import com.personal.hjycommunitymodule.common.utils.RedisCache;
import com.personal.hjycommunitymodule.community.domain.LoginUser;
import com.personal.hjycommunitymodule.community.domain.SysMenu;
import com.personal.hjycommunitymodule.community.domain.SysUser;
import com.personal.hjycommunitymodule.community.domain.vo.LoginBody;
import com.personal.hjycommunitymodule.community.domain.vo.MenuVo;
import com.personal.hjycommunitymodule.community.domain.vo.MetaVo;
import com.personal.hjycommunitymodule.community.mapper.SysMenuMapper;
import com.personal.hjycommunitymodule.community.service.LoginService;
import com.personal.hjycommunitymodule.community.service.SysMenuService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @ClassName LoginServiceImpl
 * @Author liupanpan
 * @Date 2025/12/9
 * @Description
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private SysMenuMapper menuMapper;

    // 2
    @Override
    public BaseResponse login(LoginBody user) {

        // 校验验证码
        String key = Constants.CAPTCHA_CODE_KEY + user.getUuid();
        String code = redisCache.getCacheObject(key);
        redisCache.deleteObject(key);

        if (Objects.isNull(code) || !code.equalsIgnoreCase(user.getCode())) {
            throw new CustomException(400,"验证码错误");
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        // 调用(AuthenticationManager 实现类ProviderManager 委托给)DaoAuthenticationProvider的authenticate方法进行认证
        Authentication authenticate = authenticationManager.authenticate(authentication);
        if (authenticate == null) {
            throw new CustomException(401,"登录失败");// 认证失败
        }
        // 认证成功，使用userId生成token
        LoginUser principal = (LoginUser) authenticate.getPrincipal();
//        Long userId = principal.getSysUser().getUserId();
        String jwt = JWTUtils.createJWT(principal,redisCache); // 生成token

        // 将用户信息存储到redis缓存中 - 创建令牌时实现
//        redisCache.setCacheObject(Constants.LOGIN_USER_KEY+userId,principal,30, TimeUnit.MINUTES);

        HashMap<String, String> map = new HashMap<>();
        map.put("token",jwt);
        return BaseResponse.success(map);
    }

    @Override
    public BaseResponse logout() {

        LoginUser loginUser = getCurUser();
        Long userId = loginUser.getSysUser().getUserId();
        // 删除redis缓存
        boolean b = redisCache.deleteObject("login:" + userId);
        if (b) {
            return BaseResponse.success("已退出登录");
        }
        return BaseResponse.success("退出失败");
    }

    @Override
    public ChainedMap getInfo() {
        // 获取当前登录用户信息
        LoginUser loginUser = getCurUser();

        SysUser sysUser = loginUser.getSysUser();
        // 获取权限信息
        List<String> permissions = loginUser.getPermissions();
        // 获取角色信息
        List<String> roles = loginUser.getRoles();

        return ChainedMap.create()
                .set("code",200)
                .set("msg","操作成功")
                .set("user", sysUser)
                .set("roles", roles)
                .set("permissions", permissions);
    }

    @Override
    public List<MenuVo> getRouters() {
        // 获取当前登录用户
        LoginUser loginUser = getCurUser();
        Long userId = loginUser.getSysUser().getUserId();

        // 当前用户所拥有的菜单权限 - 需要去掉 “按钮”菜单
        List<Long> menuIds = menuMapper.selectPermsByUserId(userId).stream()
                .map(SysMenu::getMenuId).collect(Collectors.toList());

        // 查询所有有效菜单
        LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.notIn(SysMenu::getMenuType,"F") // 按钮菜单不查询
                .eq(SysMenu::getStatus,"0"); // 停用的不查询

        // 管理员显示所有菜单,其他根据user_id查询对应的菜单权限
        if (!userId.equals(1L)) {
            wrapper.in(SysMenu::getMenuId, menuIds);
        }

        Map<Long, List<SysMenu>> sysMenuMap = menuMapper.selectList(wrapper).stream()
                .collect(Collectors.groupingBy(SysMenu::getParentId));
        // 一级菜单 - 查询当前菜单的所有子菜单 - 封装为树形结构
        List<SysMenu> menus = sysMenuMap.get(0L);

        List<SysMenu> treeMenus = loadChildren(menus, sysMenuMap);

        List<MenuVo> menuVos = getMenuVos(treeMenus);

        return menuVos;
    }

    private List<SysMenu> loadChildren(List<SysMenu> menus,Map<Long, List<SysMenu>> sysMenuMap) {

        List<SysMenu> treeMenus = menus.stream().map(sysMenu -> {
            List<SysMenu> childrenMenuList = sysMenuMap.get(sysMenu.getMenuId());
            if (childrenMenuList == null || childrenMenuList.isEmpty()) {
                return sysMenu;
            }
            sysMenu.setChildren(loadChildren(childrenMenuList,sysMenuMap));
            return sysMenu;
        }).collect(Collectors.toList());
        // 查询parentId为menuId的数据
        return treeMenus;
    }

    private List<MenuVo> getMenuVos(List<SysMenu> sysMenus) {
        return sysMenus.stream().map(i -> {
            MenuVo menuVo = new MenuVo();
            menuVo.setName(getMenuName(i.getPath()));
            menuVo.setPath(getPath(i.getPath(), i.getParentId()));
            menuVo.setHidden(i.getVisible().equals("1"));
            menuVo.setComponent(getComp(i.getComponent(), i.getParentId(), i.getMenuType()));

            menuVo.setMeta(new MetaVo(i.getMenuName(), i.getIcon(), i.getIsCache() == 1));

            List<SysMenu> children = i.getChildren();
            if (i.getParentId().equals(0L) && children != null && !children.isEmpty() && UserConstants.TYPE_DIR.equals(i.getMenuType())) {
                menuVo.setRedirect("noRedirect");
                menuVo.setAlwaysShow(true);
                menuVo.setChildren(getMenuVos(children));
            }
            return menuVo;
        }).collect(Collectors.toList());
    }

    private String getComp(String component,long parentId,String menuType) {
        if (StringUtils.isBlank(component)) {
            return UserConstants.LAYOUT;
        }
        if (parentId != 0 && UserConstants.TYPE_DIR.equals(menuType)) {
            return UserConstants.PARENT_VIEW;
        }
        return component;
    }

    private String getPath(String path, Long parentId) {
        if (parentId == 0) {
            return "/" + path;
        } else {
            return path;
        }
    }

    private String getMenuName(String path) {
        return StringUtils.capitalize(path);
    }

    private LoginUser getCurUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (Objects.isNull(authentication)) {
            throw new CustomException(401,"获取用户认证信息失败,请重新登录!");
        }
        return (LoginUser) authentication.getPrincipal();
    }
}
