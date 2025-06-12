package web.mvc.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig {

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
                .requestMatchers("/index", "/members", "/members/**", "/boards").permitAll()
                .requestMatchers("/admin").hasRole("ADMIN")
                .anyRequest().authenticated());
        return http.build();

    }
}
