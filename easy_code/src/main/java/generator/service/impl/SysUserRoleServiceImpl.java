package generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import generator.domain.SysUserRole;
import generator.service.SysUserRoleService;
import generator.mapper.SysUserRoleMapper;
import org.springframework.stereotype.Service;

/**
* @author liupanpan
* @description 针对表【sys_user_role(用户和角色关联表)】的数据库操作Service实现
* @createDate 2025-12-11 17:42:35
*/
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole>
    implements SysUserRoleService{

}




