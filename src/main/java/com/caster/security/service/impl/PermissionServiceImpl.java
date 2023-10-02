package com.caster.security.service.impl;

import com.caster.security.entity.Permission;
import com.caster.security.mapper.PermissionMapper;
import com.caster.security.service.PermissionService;
import com.github.yulichang.base.MPJBaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 權限表 服务实现类
 * </p>
 *
 * @author Caster
 * @since 2022-07-05
 */
@Service
public class PermissionServiceImpl extends MPJBaseServiceImpl<PermissionMapper, Permission> implements PermissionService {
}
