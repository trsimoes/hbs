package pt.eden.hbs.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("pt.eden.hbs")
public class WebServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebServerApplication.class);
    }
}