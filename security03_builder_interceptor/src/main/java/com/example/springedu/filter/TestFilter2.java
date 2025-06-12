package com.example.springedu.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import java.io.IOException;

//@Component
@Slf4j
@WebFilter(urlPatterns = {"/hello"})
@Order(1) // 우선순위 설정
public class TestFilter2 implements Filter {
    public TestFilter2() {
        System.out.println ("TestFilter2 생성");
    }
    
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
                        throws IOException, ServletException {
        log.info("[필터2] 요청 자원 수행 전");
        chain.doFilter(request, response);
        log.info("[필터2] 요청 자원 수행 후");
    }
    
    
}
