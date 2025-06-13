package web.mvc.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import web.mvc.domain.Member;
import web.mvc.service.MemberService;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/index")
    public String index(){
        log.info("index 요청됨");
        return "Spring Security Setting 완료";
    }

    @GetMapping("/members/{id}")
    public String duplicateCheck(@PathVariable("id") String id){
        log.info("id:{}",id);
        return memberService.duplicateCheck(id);

    }

    @PostMapping("/members")
    public String signUP(@RequestBody Member member){
        memberService.signUp(member);
        return "ok";
    }


}
