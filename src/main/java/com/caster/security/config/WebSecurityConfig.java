package com.caster.security.config;

import com.caster.security.config.filter.AuthenticationFailEntryPoint;
import com.caster.security.config.filter.AuthenticationFilter;
import com.caster.security.config.voter.impl.APIPermissionVoter;
import com.caster.security.config.voter.impl.PublicResourceVoter;
import com.caster.security.config.voter.impl.UserTypeVoter;
import com.caster.security.service.ApiUrlService;
import com.caster.security.service.RolesService;
import com.caster.security.service.UserService;
import com.caster.security.utils.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;


/**
 * @author caster.hsu
 * @Since 2023/5/29
 */
@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(
//        securedEnabled = true,
//        jsr250Enabled = true,
//        prePostEnabled = true
//)
@EnableAutoConfiguration(exclude = {ErrorMvcAutoConfiguration.class})
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final SystemUserDetailsService userDetailsService;
    private final RolesService rolesService;
    private final UserService userService;
    private final JwtProvider jwtProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//      @formatter:off

        http
                .cors(o -> o.configurationSource(corsConfigurationSource())) // 啟用跨域設定
                .csrf(o -> o.csrfTokenRepository(generateSCRFToken())) // 使用 CSRF 令牌保護
                .addFilterBefore(new AuthenticationFilter(userDetailsService, userService, jwtProvider), UsernamePasswordAuthenticationFilter.class) // 在驗證前添加自定義的身份驗證過濾器
                .exceptionHandling(o -> o.authenticationEntryPoint(new AuthenticationFailEntryPoint())) // 錯誤處理，指定未驗證的請求的處理方式
//                .sessionManagement(o -> o.disable()) // session 機制設定
//                .anonymous(o -> o.disable()) // 匿名訪問設定
                .authorizeRequests(o ->
                                // 這邊設定僅只忽略權限驗證, 還是會進入過濾器
//                        o.antMatchers("/system/info").permitAll()
                                o.anyRequest()
                                        .authenticated()
                                        .accessDecisionManager(accessDecisionManager())// 自定義訪問決策管理器, 可使用自訂義投票方式, 或是自行實作 DecisionManager
                                        .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                                            public <O extends FilterSecurityInterceptor> O postProcess(O fsi) {
                                                fsi.setSecurityMetadataSource(new CustomSecurityMetadataSource(rolesService)); // 使用自定義的安全元數據來源
                                                return fsi;
                                            }
                                        })
                )
        ;
        // @formatter:ON
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setExposedHeaders(Arrays.asList("*"));

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
    }

    @Override
    public void configure(WebSecurity web) {
//        web.debug(true);
        web.ignoring()
                .antMatchers("/system/login")
                .antMatchers("/auth")
                .antMatchers("/auth/login")
//                .antMatchers("/system/**")
                .antMatchers("/static/**")
        ;
    }

    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                //添加映射路徑
                registry.addMapping("/**")
                        //放行哪些原始域
                        .allowedOriginPatterns("*")
                        //是否發送Cookie信息
                        .allowCredentials(true)
                        //放行哪些原始域(請求方式)
                        .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE")
                //放行哪些原始域(頭部信息)
                //.allowedHeaders("*")
                //暴露哪些頭部信息（因為跨域訪問默認不能獲取全部頭部信息）
                //.exposedHeaders("Header1", "Header2")
                ;
            }


            /**
             * 最終手段 !!!
             * 手動把 HttpMessageConverter -> MappingJackson2HttpMessageConverter
             * 移除spring boot 內建 MappingJackson2HttpMessageConverter, 再重新複寫自定義序列化的方法
             * Bean to JsonString 自定義序列化的方法
             * 但已經用不到了 2023/06/05 Caster
             */
//            @Override
//            public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
//                converters.stream().forEach(o -> log.debug(o.toString()));
//            }
        };
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public AccessDecisionManager accessDecisionManager() {
        /**
         * 可在這邊自訂義決策管理以及投票機制, 方便調整權限控制的顆粒度. 使用default spring boot security 提共的
         * 或是可自行實作 AccessDecisionManager
         */
//        return new AffirmativeBased(Arrays.asList(new CustomAccessDecisionVoter()));
        return new AffirmativeBased(Arrays.asList(new APIPermissionVoter(userService), new UserTypeVoter(), new PublicResourceVoter()));
    }

    @Bean
    public CookieCsrfTokenRepository generateSCRFToken(){
        CookieCsrfTokenRepository r = new CookieCsrfTokenRepository();
//        r.setCookieMaxAge(10);
        r.setCookieHttpOnly(true);
        return r;
    }
}
