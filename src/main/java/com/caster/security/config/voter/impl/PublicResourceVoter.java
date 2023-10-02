package com.caster.security.config.voter.impl;

import com.caster.security.config.voter.BaseVoter;
import com.caster.security.constants.SystemConstants;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;

import java.util.Collection;

/**
 * @author caster.hsu
 * @Since 2023/9/27
 */
public class PublicResourceVoter implements BaseVoter<FilterInvocation> {
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
        int result;

        if (SystemConstants.PATH_LIST.contains(object.getHttpRequest().getServletPath()))
            result = ACCESS_GRANTED;
        else
            result = ACCESS_DENIED;

        printResult(result);
        return result;
    }

    @Override
    public String getVoterName() {
        return "公共資源檢查";
    }
}
