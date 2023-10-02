package com.caster.security.config.voter.impl;

import com.caster.security.config.voter.BaseVoter;
import com.caster.security.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;

import java.util.Collection;
import java.util.Objects;

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
        int result = ACCESS_DENIED;
        if (Objects.nonNull(authentication) && BooleanUtils.isTrue(authentication.isAuthenticated())) {
            for (ConfigAttribute attribute : configAttributes) {
                for (GrantedAuthority authority : authentication.getAuthorities()) {
                    if (StringUtils.equals(authority.getAuthority(), attribute.getAttribute()))
                        result = ACCESS_GRANTED;
                }
            }
        }
        // 輸出結果及權限資訊
        printResult(result, authentication, configAttributes);
        return result;
    }

    @Override
    public String getVoterName() {
        return "API URL 檢查";
    }
}
