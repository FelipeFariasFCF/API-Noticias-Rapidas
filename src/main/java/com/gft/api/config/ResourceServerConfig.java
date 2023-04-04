package com.gft.api.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@RequiredArgsConstructor
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private final JwtTokenStore tokenStore;
    private static final String[] PUBLIC = {"/oauth/token"};
    private static final String[] USER = {"/v1/tags/user",
                                          "/v1/news"};
    private static final String[] ADMIN = {"/v1/tags/**",
                                           "/v1/users",
                                           "/v1/news/topTrending"};
    private static final String[] ADMIN_AND_USER = {"/v1/news/historic"};

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.tokenStore(tokenStore);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(PUBLIC).permitAll()
                .antMatchers(ADMIN_AND_USER).hasAnyRole("USER", "ADMIN")
                .antMatchers(USER).hasRole("USER")
                .antMatchers(ADMIN).hasRole("ADMIN")
                .anyRequest().authenticated();
    }
}