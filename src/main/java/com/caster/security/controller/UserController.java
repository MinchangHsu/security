package com.caster.security.controller;

import com.caster.security.config.UserPrincipal;
import com.caster.security.config.anno.CurrentUser;
import com.caster.security.entity.User;
import com.caster.security.enums.SuccessCodeMsg;
import com.caster.security.exception.SystemException;
import com.caster.security.model.response.JSONResult;
import com.caster.security.service.UserService;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author caster.hsu
 * @Since 2023/9/28
 */
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/user")
@RestController
public class UserController {
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @GetMapping(value = "/info", name = "查詢使用者__QUERY_USER")
    public ResponseEntity currentUserInfo(@CurrentUser UserPrincipal currentUser) {
        log.debug("currentUserId:{}", currentUser.getId());
        return ResponseEntity.ok(JSONResult.createResult(SuccessCodeMsg.COMMON_OK)
                .addResult("userInfo", userService.lambdaQuery().eq(User::getId, currentUser.getId()).one()));
    }

    @GetMapping(value = "/{userId}", name = "查詢使用者__QUERY_USER")
    public ResponseEntity otherUserInfo(@CurrentUser UserPrincipal currentUser, @PathVariable Integer userId) {
        log.debug("currentUserId:{}, path var:{}", currentUser.getId(), userId);
        return ResponseEntity.ok(JSONResult.createResult(SuccessCodeMsg.COMMON_OK)
                .addResult("userInfo", userService.lambdaQuery().eq(User::getId, userId).one()));
    }

    @PostMapping(value = "", name = "新增使用者__ADD_USER")
    public ResponseEntity insert(@RequestBody JsonNode payload) {
        log.debug(payload.toPrettyString());
        Optional<JsonNode> loginOpt = Optional.ofNullable(payload.get("loginId"));
        Optional<JsonNode> pwdOpt = Optional.ofNullable(payload.get("pwd"));
        Optional<JsonNode> nameOpt = Optional.ofNullable(payload.get("name"));
        if(!loginOpt.isPresent() || !pwdOpt.isPresent() || !nameOpt.isPresent())
            throw new SystemException("G La..........");

        userService.save(new User()
                .setLoginId(loginOpt.get().asText())
                .setPwd(passwordEncoder.encode(pwdOpt.get().asText()))
                .setName(nameOpt.get().asText()));
        return ResponseEntity.ok(JSONResult.createResult(SuccessCodeMsg.COMMON_OK));
    }

    @PostMapping(value = "/{userId}", name = "刪除使用者__DELETE_USER")
    public ResponseEntity delete(@PathVariable Integer userId) {
        userService.lambdaUpdate().eq(User::getId, userId).remove();
        return ResponseEntity.ok(JSONResult.createResult(SuccessCodeMsg.COMMON_OK));
    }

    @PostMapping(value = "/V2", name = "刪除使用者__DELETE_USER")
    public ResponseEntity insert2(@CurrentUser UserPrincipal currentUser) {
        log.debug("insert2 >>> id:{}", currentUser.getId());
        return ResponseEntity.ok(JSONResult.createResult(SuccessCodeMsg.COMMON_OK));
    }
}
