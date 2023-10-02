package com.caster.security.config.voter.impl;

import com.caster.security.config.voter.BaseVoter;
import com.caster.security.entity.ApiUrl;
import com.caster.security.service.ApiUrlService;
import com.caster.security.service.UserService;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
public class APIPermissionVoter implements BaseVoter<FilterInvocation> {

    private UserService userService;

    public APIPermissionVoter(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public int vote(Authentication authentication, FilterInvocation object, Collection<ConfigAttribute> configAttributes) {

        userService.selectJoinList(ApiUrl.class,
                new MPJLambdaWrapper<>());

        int result = ACCESS_DENIED;
        if (Objects.nonNull(authentication) && BooleanUtils.isTrue(authentication.isAuthenticated())) {
            // 輸出權限資訊
//            printPermissionInfo(authentication, configAttributes);
            for (ConfigAttribute attribute : configAttributes) {
                for (GrantedAuthority authority : authentication.getAuthorities()) {
                    if (StringUtils.equals(authority.getAuthority(), attribute.getAttribute()))
                        return ACCESS_GRANTED;
                }
            }
        }
        printResult(result);
        return result;
    }

    public void printPermissionInfo(Authentication authentication, Collection<ConfigAttribute> configAttributes) {
        String requirePermission = configAttributes.size() != 0 ?
                configAttributes.stream().map(ConfigAttribute::getAttribute).collect(Collectors.joining(",", "[", "]"))
                : "ROLE_ANONYMOUS";
        log.info("require permission:{}", requirePermission);

        log.info("current user granted Authority:{}", authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(",", "[", "]")));
    }

    @Override
    public String getVoterName() {
        return "API URL檢查";
    }
}
