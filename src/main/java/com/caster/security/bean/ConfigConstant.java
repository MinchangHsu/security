package com.caster.security.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("ConfigConstant")
public class ConfigConstant {

    public static String jwtSecret;
    public static long jwtExpirationInMs;
    public static String domain;

    @Value("${custom.jwtSecret}")
    private void setJwtSecret(String jwtSecret) {
        ConfigConstant.jwtSecret = jwtSecret;
    }

    @Value("${custom.jwtExpirationInMs}")
    private void setJwtExpirationInMs(int jwtExpirationInMs) {
        ConfigConstant.jwtExpirationInMs = jwtExpirationInMs;
    }

    @Value("${custom.domain}")
    private void setDomain(String domain) {
        ConfigConstant.domain = domain;
    }

}
