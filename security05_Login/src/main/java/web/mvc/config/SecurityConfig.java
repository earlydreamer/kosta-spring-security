package web.mvc.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import web.mvc.jwt.LoginFilter;
import web.mvc.jwt.JWTUtil;

@Configuration
@EnableWebSecurity
@Slf4j
@RequiredArgsConstructor
public class SecurityConfig {
    //AuthenticationManager 가 인자로 받을 AuthenticationConfiguraion 객체 생성자 주입
    private final AuthenticationConfiguration authenticationConfiguration;
    private final JWTUtil jwtUtil;
    //AuthenticationManager Bean 등록
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        log.info("BCryptPasswordEncoder");
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        log.info("SecurityFilterChain call ...");

        //사용하지 않는 부분 Disable해준다.
        //Form 로그인 방식 Disable (JWT인증 사용예정)
        //HTTP Basic 방식 disbale
        http.csrf((auth -> auth.disable()));
        http.formLogin((auth -> auth.disable()));
        http.httpBasic((auth -> auth.disable()));

        //경로별 권한 인가(authorize)
        http.authorizeHttpRequests((auth) -> auth
                .requestMatchers(HttpMethod.POST, "/login").permitAll()
                .requestMatchers("/index", "/members", "/members/**", "/boards").permitAll()
                .requestMatchers("/admin").hasRole("ADMIN")
                .anyRequest().authenticated());


        http.addFilterAt(
                new LoginFilter( this.authenticationManager(authenticationConfiguration) , jwtUtil) ,
                UsernamePasswordAuthenticationFilter.class
        );

        return http.build();



    }
}
