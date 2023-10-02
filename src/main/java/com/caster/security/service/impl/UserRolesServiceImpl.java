package com.caster.security.service.impl;

import com.caster.security.entity.FunctionInfo;
import com.caster.security.entity.Permission;
import com.caster.security.entity.RolePermission;
import com.caster.security.entity.UserRoles;
import com.caster.security.mapper.UserRolesMapper;
import com.caster.security.model.view.FunctionInfoView;
import com.caster.security.model.view.RolesInfoView;
import com.caster.security.service.FunctionInfoService;
import com.caster.security.service.PermissionService;
import com.caster.security.service.RolesService;
import com.caster.security.service.UserRolesService;
import com.caster.security.utils.BeanLazyUtil;
import com.github.yulichang.base.MPJBaseServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Caster
 * @since 2022-07-05
 */
@Service
@RequiredArgsConstructor
public class UserRolesServiceImpl extends MPJBaseServiceImpl<UserRolesMapper, UserRoles> implements UserRolesService {
    private final RolesService rolesService;
    private final FunctionInfoService functionService;
    private final PermissionService permissionService;

    @Override
    public RolesInfoView getRoleDetail(Integer roleId) {
        final Map<Integer, FunctionInfo> functionMap = functionService.list().stream().collect(Collectors.toMap(FunctionInfo::getId, v -> v));

        RolesInfoView rolesInfoView = BeanLazyUtil.beanCopy(rolesService.getById(roleId), new RolesInfoView());

        List<Permission> permissions = permissionService.selectJoinList(Permission.class,
                new MPJLambdaWrapper<>().selectAll(Permission.class)
                        .innerJoin(RolePermission.class, on -> on.eq(RolePermission::getPermissionId, Permission::getId)
                                .eq(RolePermission::getRoleId, roleId)));

        Map<Integer, List<Permission>> functionListMap = permissions.stream()
                .collect(Collectors.groupingBy(Permission::getFunctionId, Collectors.mapping(v -> v, Collectors.toList())));

        List<FunctionInfoView> functionInfoViews = new ArrayList<>();
        for (Integer functionId : functionListMap.keySet()) {
            FunctionInfoView functionInfoView = BeanLazyUtil.beanCopy(functionMap.get(functionId), new FunctionInfoView());
            functionInfoView.setPermissionList(functionListMap.get(functionId));
            functionInfoViews.add(functionInfoView);
        }
        rolesInfoView.setFunctionInfoViews(functionInfoViews);
        return rolesInfoView;
    }
}
