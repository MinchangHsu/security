package com.caster.security.config;

import com.caster.security.entity.*;
import com.caster.security.service.UserService;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class SystemUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        return UserPrincipal.create(null, userService.lambdaQuery().eq(User::getLoginId, loginId).one());
    }

    public UserDetails loadUserById(HttpServletRequest request, Integer id) throws UsernameNotFoundException {
        return UserPrincipal.create(request, userService.lambdaQuery().eq(User::getId, id).one())
                .setAuthorities(userService.selectJoinList(Roles.class,
                        new MPJLambdaWrapper<>().selectAll(Roles.class)
                                .innerJoin(User.class, on -> on.eq(User::getId, id))
                                .innerJoin(UserRoles.class, UserRoles::getUserId, User::getId)
                                .innerJoin(Roles.class, Roles::getId, UserRoles::getRoleId)
                                .groupBy(Roles::getId)).stream().map(o -> new SimpleGrantedAuthority(o.getName())).collect(Collectors.toList()));
    }
}
