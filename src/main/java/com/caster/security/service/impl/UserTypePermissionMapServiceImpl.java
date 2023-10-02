package com.caster.security.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.caster.security.entity.UserTypePermissionMap;
import com.caster.security.mapper.UserTypePermissionMapMapper;
import com.caster.security.service.UserTypePermissionMapService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 使用者類型權限對應表 服务实现类
 * </p>
 *
 * @author caster
 * @since 2022-11-14
 */
@Service
public class UserTypePermissionMapServiceImpl extends ServiceImpl<UserTypePermissionMapMapper, UserTypePermissionMap> implements UserTypePermissionMapService {

}
