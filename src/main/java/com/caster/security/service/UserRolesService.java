package com.caster.security.service;

import com.caster.security.entity.UserRoles;
import com.caster.security.model.view.RolesInfoView;
import com.github.yulichang.base.MPJBaseService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Caster
 * @since 2022-07-05
 */
public interface UserRolesService extends MPJBaseService<UserRoles> {

    RolesInfoView getRoleDetail(Integer roleId);

}
