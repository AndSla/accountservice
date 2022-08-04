package com.learning.accountservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    PasswordEncoder encoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(encoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .mvcMatchers(HttpMethod.POST, "/api/auth/changepass")
                .hasAnyRole("ADMIN", "ACCOUNTANT", "USER")
                .mvcMatchers(HttpMethod.GET, "/api/empl/payment")
                .hasAnyRole("ACCOUNTANT", "USER")
                .mvcMatchers(HttpMethod.POST, "/api/acct/payments")
                .hasAnyRole("ACCOUNTANT")
                .mvcMatchers(HttpMethod.PUT, "/api/acct/payments")
                .hasAnyRole("ACCOUNTANT")
                .mvcMatchers(HttpMethod.GET, "/api/admin/user")
                .hasAnyRole("ADMIN")
                .mvcMatchers(HttpMethod.DELETE, "/api/admin/user")
                .hasAnyRole("ADMIN")
                .mvcMatchers(HttpMethod.PUT, "/api/admin/user/role")
                .hasAnyRole("ADMIN")
                .mvcMatchers(HttpMethod.POST, "/api/auth/signup")
                .permitAll()
                .and()
                .csrf().disable().headers().frameOptions().disable()    // for H2 console to work
                .and()
                .httpBasic();
    }

}
