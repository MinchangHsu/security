package com.caster.security.controller;

import com.caster.security.bean.ConfigConstant;
import com.caster.security.config.UserPrincipal;
import com.caster.security.controller.request.LoginReq;
import com.caster.security.entity.User;
import com.caster.security.enums.SuccessCodeMsg;
import com.caster.security.enums.TokenType;
import com.caster.security.model.JsonWebToken;
import com.caster.security.model.response.JSONResult;
import com.caster.security.service.UserService;
import com.caster.security.utils.DateTimeUtil;
import com.caster.security.utils.HttpReqResUtil;
import com.caster.security.utils.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


/**
 * Author: Caster
 * Date: 2021/09/21
 * Comment:
 */
@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final UserService userService;


    @PostMapping(value = "/login", name = "登入")
    public ResponseEntity authenticateUser(@RequestBody LoginReq loginReq) {

        log.trace("current login:{}", loginReq.toString());
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginReq.getLoginId(), loginReq.getPassword()));

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = userService.lambdaQuery().eq(User::getId, userPrincipal.getId()).one();

        JsonWebToken jwtObj = new JsonWebToken();
        Map<String, Object> extra = new HashMap<>();

        extra.put("type", TokenType.LOGIN.getType());
        jwtObj.setExtra(extra);
        jwtObj.setExpireTimeMs(ConfigConstant.jwtExpirationInMs);
        jwtObj.setSecretStr(ConfigConstant.jwtSecret);

        String loginToken = jwtProvider.generateUserLoginToken(user.getId().longValue(), jwtObj);
        log.trace("current login token:{}", loginToken);

//        boolean isNeedGoogleAuth = userService.checkEnableGoogleAuthenticator(user.getUserType());
//
//        if (isNeedGoogleAuth && !GoogleAuthenticatorUtil.verify(user.getGoogleAuthSecretKey(), loginReq.getGoogleValidateCode()))
//            return ResponseEntity.ok(JSONResult.createResult(GpErrorCodeMsg.GOOGLE_VALIDATION_CODE_ERROR));
//
//        boolean isIllegalLoginIp = userService.checkUserLoginIp(user.getUserType(), user.getLoginIpAddress());
//
//        if (isIllegalLoginIp)
//            return ResponseEntity.ok(JSONResult.createResult(GpErrorCodeMsg.WHITELIST_VALIDATION_CODE_ERROR));

        user.setLoginIpAddress(HttpReqResUtil.getClientIpAddress())
                .setLastAccessDate(DateTimeUtil.getNowLocal())
                .setUpdateDate(null);

        userService.saveOrUpdate(user);

        return ResponseEntity
                .ok(JSONResult.createResult(SuccessCodeMsg.COMMON_OK)
                        .addResult("userInfo", user)
                        .addResult("authentication", loginToken));
    }
}
