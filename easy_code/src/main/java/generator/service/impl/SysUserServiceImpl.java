package generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import generator.domain.SysUser;
import generator.service.SysUserService;
import generator.mapper.SysUserMapper;
import org.springframework.stereotype.Service;

/**
* @author liupanpan
* @description 针对表【sys_user(用户信息表)】的数据库操作Service实现
* @createDate 2025-12-09 19:23:28
*/
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser>
    implements SysUserService{

}




