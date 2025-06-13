package web.mvc.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import web.mvc.domain.Member;
import web.mvc.repository.MemberRepository;
import web.mvc.security.CustomMemberDetails;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomDetailsService {
    private final MemberRepository memberRepository;

    public UserDetails loadUserByUserName(String username) throws UsernameNotFoundException {

        Member findmember = memberRepository.findById(username);

        if(findmember!=null){
            log.info("찾았음 = {}",findmember);
            return new CustomMemberDetails(findmember);
        }
        return null;
    }

}
