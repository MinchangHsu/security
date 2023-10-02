package com.caster.security.controller;

import com.caster.security.service.ApiUrlService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("/system")
@RestController
@RequiredArgsConstructor
public class SystemController {
    private static final Logger log = LoggerFactory.getLogger(SystemController.class);
    private final RedisTemplate redisTemplate;
    private final ApiUrlService apiUrlService;

    @PostMapping("/info")
    public ResponseEntity info(@CurrentSecurityContext SecurityContext context) {
        log.info("info !!!");
        if (context.getAuthentication() instanceof AnonymousAuthenticationToken) {
            log.info("anonymous");
        } else {
            log.info("not anonymous");
        }

        apiUrlService.lambdaQuery().list().forEach(o -> log.debug("" + o));

        return ResponseEntity.ok(RandomStringUtils.randomAlphabetic(10));
    }

    @RequestMapping("/errorHandle")
    public ModelAndView handleError() {
        ModelAndView model = new ModelAndView("/error");
        model.addObject("httpStatus", 404);
        model.addObject("message", "the page you are looking for not available!");
        return model;
    }

}
