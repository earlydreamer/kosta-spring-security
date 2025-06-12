package web.mvc.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.mvc.domain.Member;
import web.mvc.exception.ErrorCode;
import web.mvc.exception.MemberAuthenticationException;
import web.mvc.repository.MemberRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    @Transactional(readOnly = true)
    public String duplicateCheck(String id) {
        Member member = memberRepository.duplicateCheck(id);
        System.out.println(member);
        if(member==null) return "사용 가능";
        else return "중복";
    }

    @Override
    @Transactional
    public void signUp(Member member) {
        if(memberRepository.existById(member.getId())){
            throw new MemberAuthenticationException(ErrorCode.DUPLICATED);
        }
        member.setPwd(passwordEncoder.encode(member.getPwd()));
        member.setRole("ROLE_USER");

        Member m = memberRepository.save(member);
        log.info("m={}",m);
    }
}
