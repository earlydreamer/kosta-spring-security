package web.mvc;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import web.mvc.domain.Member;
import web.mvc.repository.MemberRepository;


@SpringBootTest
@Slf4j
public class AdminTest {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MemberRepository memberRepository;


    @Test
    @DisplayName("암호화 test")
    void passwordTest() {
        String rawPassword = "123456";

        String encodedPassword = passwordEncoder.encode(rawPassword);
        log.info("EncodedPassword={}", encodedPassword);

        boolean isPasswordMatch = passwordEncoder.matches(rawPassword, encodedPassword);
        log.info("isPasswordMatch={}", isPasswordMatch);

    }


    @DisplayName("관리자 계정 추가 테스트")
    @Test
    void memberInsert() {
        String encPwd = passwordEncoder.encode("1234");

        memberRepository.save(
                Member.builder()
                        .id("admin")
                        .pwd(encPwd)
                        .role("ROLE_ADMIN")
                        .address("주소")
                        .name("이름")
                        .build());
    }
}
