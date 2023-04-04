package com.heeseung.community1.config;

import com.heeseung.community1.security.AuthFailureHandler;
import com.heeseung.community1.security.AuthSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.httpBasic().disable();
        http.authorizeRequests()
                .antMatchers("/checkout", "/member/mypage")
                .authenticated()
                .antMatchers("/Notice/**/PostModify", "/Notice/newPost", "/api/admin/**")
                .hasAuthority("3")  // 1:user, 2:mod, 3:admin, other:anonymousUser
                .anyRequest()
                .permitAll()

                .and()
                .formLogin()
                .usernameParameter("username")
                .loginPage("/member/login")
                .loginProcessingUrl("/member/login")
                .successHandler(new AuthSuccessHandler())   //defaultUrl 지정 가능, 파라미터 String ("/Notice"), 기본 생성자는 ("/")
                .failureHandler(new AuthFailureHandler());
        http.headers().frameOptions().sameOrigin();
    }
}
