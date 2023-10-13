package com.caster.security.controller;

import com.caster.security.enums.SuccessCodeMsg;
import com.caster.security.model.response.JSONResult;
import com.caster.security.service.ApiUrlService;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

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

        return ResponseEntity.ok(JSONResult.createResult(SuccessCodeMsg.COMMON_OK));
    }

    @RequestMapping("/errorHandle")
    public ModelAndView handleError() {
        ModelAndView model = new ModelAndView("/error");
        model.addObject("httpStatus", 404);
        model.addObject("message", "the page you are looking for not available!");
        return model;
    }

    @PostMapping(value = "/test")
    public ResponseEntity test(@RequestBody JsonNode payload, HttpServletRequest request, @RequestHeader HttpHeaders headers) {
        log.debug(payload.toPrettyString());


        JsonNode mobilePhone = payload.get("mobilePhone");
        JsonNode cccc = payload.get("cccc");
        log.debug("ccc is present ? {}", Optional.ofNullable(cccc).isPresent());
        log.debug("mobilePhone is Integral Number ? {}", mobilePhone.isIntegralNumber()); // false
        log.debug("mobilePhone is int ? {}", mobilePhone.isInt());
        log.debug("mobilePhone as int ? 0 or 1 > {}", mobilePhone.asInt());
        log.debug("mobilePhone as text ? > {}", mobilePhone.asText());
        log.debug("mobilePhone as int ? 0 or 1 default -1 > {}", mobilePhone.asInt(-1));
        log.debug("mobilePhone is big integer ? {}", mobilePhone.isBigInteger());
        log.debug("mobilePhone can convert to int ? {}", mobilePhone.canConvertToInt());
        log.debug("mobilePhone can convert to long ? {}", mobilePhone.canConvertToLong());


        Optional<JsonNode> mobilePhoneOpt = Optional.ofNullable(mobilePhone);


        return ResponseEntity.ok(JSONResult.createResult(SuccessCodeMsg.COMMON_OK));
    }

}
