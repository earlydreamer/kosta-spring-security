package web.mvc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth->auth.anyRequest().authenticated())//모든 요청은 인증해야해
        //http.authorizeHttpRequests(auth->auth.anyRequest().permitAll())//모든 요청은 인증안해도돼
                .formLogin(Customizer.withDefaults())
                .httpBasic(httpBasic -> httpBasic.disable());

        return http.build();
    }

    /**
     *  in-memory 계정 추가
     * */
    @Bean
    public UserDetailsService userDetailsService() {
       UserDetails user1 = User.withUsername("kosta").password("{noop}1234").roles("USER").build();
       UserDetails user2 = User.withUsername("kim").password("{noop}1234").roles("USER").build();

       return new InMemoryUserDetailsManager(user1, user2);
    }
}
