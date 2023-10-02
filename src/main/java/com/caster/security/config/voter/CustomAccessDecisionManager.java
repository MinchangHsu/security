package com.caster.security.config.voter;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;

import java.util.Collection;
import java.util.stream.Collectors;

public class CustomAccessDecisionManager implements AccessDecisionManager {
    private Logger log = LoggerFactory.getLogger(CustomAccessDecisionManager.class);

    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
            throws AccessDeniedException, InsufficientAuthenticationException {
        String requirePermission = configAttributes.size() != 0 ?
                configAttributes.stream().map(ConfigAttribute::getAttribute).collect(Collectors.joining(",", "[", "]"))
                : "ROLE_ANONYMOUS";
        log.debug("require permission:{}", requirePermission);

        log.debug("current user granted Authority:{}", authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(",", "[", "]")));
        for (ConfigAttribute attribute : configAttributes) {
            for (GrantedAuthority authority : authentication.getAuthorities()) {
                if (StringUtils.equals(authority.getAuthority(), attribute.getAttribute()))
                    return;
            }
        }
        throw new AccessDeniedException("當前訪問沒有權限");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);

    }
}
