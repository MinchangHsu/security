package com.caster.security.config.filter;

import com.caster.security.bean.ConfigConstant;
import com.caster.security.config.SystemUserDetailsService;
import com.caster.security.service.UserService;
import com.caster.security.utils.JwtProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class AuthenticationFilter extends OncePerRequestFilter {
    private static final Logger log = LoggerFactory.getLogger(AuthenticationFilter.class);

    private SystemUserDetailsService userDetailsService;
    private UserService userService;
    private JwtProvider jwtProvider;

    public AuthenticationFilter(SystemUserDetailsService userDetailsService, UserService userService, JwtProvider jwtProvider) {
        this.userDetailsService = userDetailsService;
        this.userService = userService;
        this.jwtProvider = jwtProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws IOException {
        try {
            String jwt = getJwtFromRequest(request);

            if (StringUtils.isNotBlank(jwt) && jwtProvider.validate(jwt, ConfigConstant.jwtSecret)) {
                Long userIdLong = jwtProvider.getUserIdFromJWT(jwt, ConfigConstant.jwtSecret);

                Integer userId = userIdLong.intValue();

                UserDetails userPrincipal = userDetailsService.loadUserById(request, userId);

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userPrincipal, null, userPrincipal.getAuthorities());

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            filterChain.doFilter(request, response);
        } catch (Exception ex) {
            log.error("Could not set user authentication in security context", ex);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setHeader("Content-Type", "application/json;charset=UTF-8");
            response.getWriter()
                    .write(new ObjectMapper()
                            .writerWithDefaultPrettyPrinter()
                            .writeValueAsString("JSONResult.createResult(ErrorCodeMsg.TOKEN_ERROR)"));
        }
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        Iterator<String> s = request.getHeaderNames().asIterator();
        while(s.hasNext()){
            System.out.println(s.next());
        }

        if (StringUtils.startsWithIgnoreCase(bearerToken, "Bearer ")) {
            return StringUtils.remove(bearerToken, "Bearer ");
        }
        return null;
    }
}
