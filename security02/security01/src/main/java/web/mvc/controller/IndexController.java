package web.mvc.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class IndexController {
    @GetMapping("/")
    public String index() {
        log.info("index calll   ......");

        return "Have a nice day!";
    }
}
