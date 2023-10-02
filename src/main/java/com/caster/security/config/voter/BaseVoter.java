package com.caster.security.config.voter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDecisionVoter;


/**
 * @author caster.hsu
 * @Since 2023/6/6
 */
public interface BaseVoter<T> extends AccessDecisionVoter<T> {
    Logger log = LoggerFactory.getLogger(BaseVoter.class);

    default void printResult(int result) {
        log.info("{} >> result:{}", getVoterName(), result == 0 ? "棄權" : (result > 0 ? "贊成" : "否決"));
    }

    String getVoterName();
}
