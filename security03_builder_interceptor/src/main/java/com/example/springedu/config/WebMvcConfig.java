package com.example.springedu.config;
import com.example.springedu.interceptor.TestInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    public WebMvcConfig() {
        System.out.println("WebMvcConfig 동작");
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TestInterceptor())
                .addPathPatterns("/hello");

        /*
        registry.addInterceptor(인터셉터객체)
                .addPathPatterns("/*")                
                .excludePathPatterns("/sample"); 
        */
    }
}

