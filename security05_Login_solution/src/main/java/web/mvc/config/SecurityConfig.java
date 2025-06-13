package web.mvc.config;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import web.mvc.jwt.JWTUtil;
import web.mvc.jwt.LoginFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {
    //AuthenticationManager가 인자로 받을 AuthenticationConfiguraion 객체 생성자 주입
    private final AuthenticationConfiguration authenticationConfiguration;// 주입
    private final JWTUtil jwtUtil; //주입(JWT 토큰을 생성하는 객체)

    //AuthenticationManager Bean 등록
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        log.info("authenticationManager ---= {}", configuration);
        return configuration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        log.info("bCryptPasswordEncoder call.....");
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        log.info("SecurityFilterChain filterChain(HttpSecurity http) call.....");
       /////////////////////////////////
        //csrf disable
        http.csrf((auth) -> auth.disable()); //csrf공격을 방어하기 위한 토큰 주고 받는 부분을 비활성화!
        //Form 로그인 방식 disable -> React, JWT 인증 방식으로 변겨예정
        //disable 를 설정하면 시큐리티의 UsernamePasswordAuthenticationFilter비활성됨.
        http.formLogin((auth) -> auth.disable());

        //http basic 인증 방식 disable
        http.httpBasic((auth) -> auth.disable());

        //경로별 인가 작업
        http.authorizeHttpRequests((auth) ->
                auth
                        .requestMatchers("/index", "/members", "/members/**", "/boards").permitAll()
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .anyRequest().authenticated());
        //필터 추가 LoginFilter()는 인자를 받음 (AuthenticationManager() 메소드에 authenticationConfiguration 객체를넣어야 함)
//addFilterAt은 UsernamePasswordAuthenticationFilter의 자리에 LoginFilter가 실행되도록 설정하는 것
        http.addFilterAt(
                new LoginFilter(
                        this.authenticationManager(authenticationConfiguration), //AuthenticationManager
                        jwtUtil), //JWTUtil
                UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}