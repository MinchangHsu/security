package com.caster.security.config.voter.impl;

import com.caster.security.config.UserPrincipal;
import com.caster.security.config.voter.BaseVoter;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;

import java.util.Collection;
import java.util.Objects;

/**
 * @author caster.hsu
 * @Since 2023/6/6
 */
public class UserTypeVoter implements BaseVoter<FilterInvocation> {
    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public int vote(Authentication authentication, FilterInvocation object, Collection<ConfigAttribute> attributes) {
        int result = ACCESS_ABSTAIN;

        if (Objects.nonNull(authentication) &&
                BooleanUtils.isFalse(authentication instanceof AnonymousAuthenticationToken) &&
                BooleanUtils.isTrue(authentication.isAuthenticated())) {
            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            if (StringUtils.equals("1", userPrincipal.getUserType()))
                result = ACCESS_GRANTED;
            else
                result = ACCESS_DENIED;
        }
        printResult(result);
        return result;
    }

    @Override
    public String getVoterName() {
        return "角色類型檢查";
    }
}
