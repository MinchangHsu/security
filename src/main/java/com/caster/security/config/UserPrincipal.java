package com.caster.security.config;

import com.caster.security.entity.User;
import com.caster.security.enums.CommonOnOffStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

public class UserPrincipal implements UserDetails {

    @JsonIgnore
    private static final long serialVersionUID = -2701740693505571140L;


    private Integer id;

    private String username; //from UserDetails
    @JsonIgnore
    private String password; //from UserDetails
    private Collection<? extends GrantedAuthority> authorities; //from UserDetails

    private String apiUrl;
    private String httpMethod;
    private String UserType;

    private UserPrincipal() {
    }

    public static UserPrincipal create(HttpServletRequest request, User user) {
        String apiUrl = "";
        String method = "";
        if (request != null) {
            apiUrl = request.getServletPath();
            method = request.getMethod();
        }
        return new UserPrincipal()
                .setId(user.getId())
                .setUsername(user.getLoginId())
                .setPassword(user.getPwd())
                .setUserType(user.getUserType())
                .setApiUrl(apiUrl)
                .setHttpMethod(method);
    }

    @Override
    public boolean isAccountNonExpired() {
        return CommonOnOffStatus.isOn("1");
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return CommonOnOffStatus.isOn("1");
    }

    public Integer getId() {
        return id;
    }

    public UserPrincipal setId(Integer id) {
        this.id = id;
        return this;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public UserPrincipal setUsername(String username) {
        this.username = username;
        return this;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public UserPrincipal setPassword(String password) {
        this.password = password;
        return this;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public UserPrincipal setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
        return this;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public UserPrincipal setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
        return this;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public UserPrincipal setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
        return this;
    }

    public String getUserType() {
        return UserType;
    }

    public UserPrincipal setUserType(String userType) {
        this.UserType = userType;
        return this;
    }
}
