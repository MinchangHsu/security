package com.caster.security.config;

import com.caster.security.entity.User;
import com.caster.security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;


@Service
@RequiredArgsConstructor
public class SystemUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        return UserPrincipal.create(null, userService.lambdaQuery().eq(User::getLoginId, loginId).one());
    }

    public UserDetails loadUserById(HttpServletRequest request, Integer id) throws UsernameNotFoundException {
        return UserPrincipal.create(request, userService.lambdaQuery().eq(User::getId, id).one());
    }
}
