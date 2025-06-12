package web.mvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

//@SpringBootApplication
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class Security04RegisterApplication {

    public static void main(String[] args) {
        SpringApplication.run(Security04RegisterApplication.class, args);
    }

}
