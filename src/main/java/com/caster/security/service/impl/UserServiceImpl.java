package com.caster.security.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.caster.security.entity.Roles;
import com.caster.security.entity.User;
import com.caster.security.entity.UserRoles;
import com.caster.security.mapper.UserMapper;
import com.caster.security.model.view.UserRolesView;
import com.caster.security.service.RolePermissionService;
import com.caster.security.service.UserRolesService;
import com.caster.security.service.UserService;
import com.github.yulichang.base.MPJBaseServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * <p>
 * 系統使用者資訊 服务实现类
 * </p>
 *
 * @author Caster
 * @since 2022-07-05
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends MPJBaseServiceImpl<UserMapper, User> implements UserService {
    private final UserMapper userMapper;
    private final UserRolesService userRolesService;
    private final RolePermissionService rolePermissionService;

    @Override
    public UserRolesView getUserRolesView(User user) {
        List<Roles> rolesList = userRolesService.selectJoinList(Roles.class,
                new MPJLambdaWrapper<>()
                        .selectAll(Roles.class)
                        .innerJoin(Roles.class, on -> on.eq(UserRoles::getRoleId, Roles::getId))
                        .eq(UserRoles::getUserId, user.getId()));
//		List<Permission> permissionList = rolePermissionService.selectJoinList(Permission.class,
//				new MPJLambdaWrapper<>()
//						.selectAll(Permission.class)
//						.innerJoin(Permission.class, on -> on.eq(RolePermission::getPermissionId, Permission::getId))
//						.in(RolePermission::getRoleId, rolesList.stream().map(Roles::getId).collect(Collectors.toSet())));
        return new UserRolesView().setUser(user).setRolesList(rolesList);
    }

    @Override
    public void insertUserRoles(Integer userId, List<String> roleNames) {

    }

    @Override
    public User getUserByLoginId(String loginId, boolean checkStatus) {
        LambdaQueryWrapper<User> wrapper = Wrappers.lambdaQuery();
        wrapper.in(StringUtils.isNotBlank(loginId), User::getLoginId, loginId);
        Optional<User> userOpt = Optional.ofNullable(userMapper.selectOne(wrapper));
        return userOpt.get();
    }

    @Override
    public User getUserById(Integer userId, boolean checkStatus) {
        LambdaQueryWrapper<User> wrapper = Wrappers.lambdaQuery();
        wrapper.in(Objects.nonNull(userId), User::getId, userId);
        Optional<User> userOpt = Optional.ofNullable(userMapper.selectOne(wrapper));
        return userOpt.get();
    }
}
