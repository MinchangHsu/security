package com.caster.security.config;


import com.caster.security.constants.SystemConstants;
import com.caster.security.entity.*;
import com.caster.security.service.RolesService;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
public class CustomSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private RolesService rolesService;

    public CustomSecurityMetadataSource(RolesService rolesService) {
        this.rolesService = rolesService;
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        FilterInvocation fi = (FilterInvocation) object;

        /**
         * 如果需要自訂義路由權限, 需要在這邊設定當前的Path 權限是甚麼, 後續再透過 AccessDecisionManager 進行投票決策.
         */
        log.debug("CustomSecurityMetadataSource incoming url:{}", fi.getHttpRequest().getServletPath());
        List<Roles> rolesList = rolesService.selectJoinList(Roles.class,
                new MPJLambdaWrapper<>().selectAll(Roles.class)
                        .innerJoin(RolePermission.class, on -> on.eq(RolePermission::getRoleId, Roles::getId))
                        .innerJoin(PermissionApi.class, on -> on.eq(PermissionApi::getPermissionId, RolePermission::getPermissionId))
                        .innerJoin(ApiUrl.class, on ->
                                on.eq(ApiUrl::getId, PermissionApi::getApiId)
                                        .eq(ApiUrl::getApiUrl, fi.getHttpRequest().getServletPath())
                                        .eq(ApiUrl::getApiHttpMethod, fi.getHttpRequest().getMethod())));

        if (Objects.isNull(rolesList) || rolesList.isEmpty())
            return SecurityConfig.createList(SystemConstants.ROLE_ADMIN); // 沒有記錄此api 給予此路徑為 最高管理員權限才可訪問
        else
            return SecurityConfig.createList(rolesList.stream().map(Roles::getName).collect(Collectors.toList()).toArray(String[]::new));

//        return null; // 默認通過不驗證路由權限
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        log.info("CustomSecurityMetadataSource supports incoming");
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}
