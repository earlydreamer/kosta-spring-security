package web.mvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class Security05LoginApplication {

    public static void main(String[] args) {
        SpringApplication.run(Security05LoginApplication.class, args);
    }

}
