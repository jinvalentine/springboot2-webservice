package com.jinuk.sutdy.springboot.config.auth;

import com.jinuk.sutdy.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity // Spring Security 설정들을 활성화시켜 준다. , 스프링 시큐리티 필터가 스프링 필터체인에 등록된다.
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // REST API에서는 csrf공격으로부터 안전하기에 disable() 해준다.
                .headers().frameOptions().disable() // h2-console(서버의 서드파티 모듈) 화면 사용을 위해 옵션들을 disable한다.
            .and()
                .authorizeRequests() // URL별 권한 관리를 설정하는 옵션의 시작점이다.
                .antMatchers("/", "/css/**", "images/**", "/js/**", "/h2-console/**", "/profile").permitAll()
                // 어떠한 보안요구 없이 요청 가능하다.
                .antMatchers("/api/v1/**").hasRole(Role.USER.name())
                // antMatchers : URL, HTTP 메소드별로 권한 관리 대상을 지정하는 옵션
                .anyRequest().authenticated()
                // anyRequest : 설정된 값들 이외 나머지 URL, autehnticated: 인증된 사용자(로그인한 사용자)만 허용
            .and()
                .logout()
                    .logoutSuccessUrl("/") // 로그아웃 성공시 이동 주소
            .and()
                .oauth2Login() // OAuth2 로그인 기능에 대한 여러 설정의 진입점
                    .userInfoEndpoint() // OAuth2 로그인 성공 이후 사용자 정보를 가져올 때의 설정들을 담당
                        .userService(customOAuth2UserService);
                        // 소셜 로그인 성공시 후속 조치를 진행할 UserService 인터페이스의 구현체를 등록한다.

    }
}
