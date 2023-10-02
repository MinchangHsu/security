package com.caster.security.config.voter;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.stream.Collectors;


/**
 * @author caster.hsu
 * @Since 2023/6/6
 */
public interface BaseVoter<T> extends AccessDecisionVoter<T> {
    Logger log = LoggerFactory.getLogger(BaseVoter.class);

    default void printResult(int result) {
        log.info("{} >> result:{}", getVoterName(), result == 0 ? "棄權" : (result > 0 ? "贊成" : "否決"));
    }

    default void printResult(int result, Authentication authentication, Collection<ConfigAttribute> configAttributes) {
        log.info("{} >> result:{}", getVoterName(), result == 0 ? "棄權" : (result > 0 ? "贊成" : "否決, \n原因:\n" + printPermissionInfo(authentication, configAttributes)));
    }

    default String printPermissionInfo(Authentication authentication, Collection<ConfigAttribute> configAttributes) {
        String requirePermission = configAttributes.size() != 0 ?
                configAttributes.stream().map(ConfigAttribute::getAttribute).collect(Collectors.joining(",", "[", "]"))
                : "ROLE_ANONYMOUS";
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("    Require permission:%s \n", requirePermission));

        sb.append(String.format("    Current user granted Authority:%s \n", authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(",", "[", "]"))));
        return sb.toString();
    }

    String getVoterName();
}
