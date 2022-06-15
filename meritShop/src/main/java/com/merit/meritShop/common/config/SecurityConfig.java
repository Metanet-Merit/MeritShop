package com.merit.meritShop.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter   {

    private final JwtUtil jwtTokenProvider;
    private final WebAccessDeniedHandler webAccessDeniedHandler;

    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    public SecurityConfig(JwtUtil jwtTokenProvider, WebAccessDeniedHandler webAccessDeniedHandler, CustomAuthenticationEntryPoint customAuthenticationEntryPoint) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.webAccessDeniedHandler = webAccessDeniedHandler;
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
    }

    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable();


        http

                .authorizeRequests()
            //    .antMatchers( "/user/login").anonymous()
                .antMatchers( "/user/logout").access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
//                .antMatchers(HttpMethod.GET,"/api/posts/**").anonymous()
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/myPage/**").access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll()
/*                .and()					//추가
                .formLogin()				// form기반의 로그인인 경우
                .loginPage("/user/login")		// 인증이 필요한 URL에 접근하면 /loginForm으로 이동
*//*                .usernameParameter("id")		// 로그인 시 form에서 가져올 값(id, email 등이 해당)
                .passwordParameter("pw")		// 로그인 시 form에서 가져올 값
                .loginProcessingUrl("/login")		// 로그인을 처리할 URL 입력*//*
                .defaultSuccessUrl("/main")			// 로그인 성공하면 "/" 으로 이동
             //   .failureUrl("/user/login")		//로그인 실패 시 /loginForm으로 이동*/
                .and()
                .cors()
                .and()
                .exceptionHandling().authenticationEntryPoint(customAuthenticationEntryPoint)
                .and()
                .exceptionHandling().accessDeniedHandler(webAccessDeniedHandler)
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
    }


}
