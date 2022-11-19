package com.heeseung.comunity1.config;

import com.heeseung.comunity1.security.AuthFailureHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.httpBasic().disable();
        http.authorizeRequests()
                .antMatchers("/checkout", "/member/mypage")
                .authenticated()
                .antMatchers("/admin/**","/api/admin/**")
                .permitAll()
                .anyRequest()
                .permitAll()

                .and()
                .formLogin()
                .usernameParameter("username")
                .loginPage("/member/login")
                .loginProcessingUrl("/member/login")
                .failureHandler(new AuthFailureHandler())
                .defaultSuccessUrl("/index");
    }
}
