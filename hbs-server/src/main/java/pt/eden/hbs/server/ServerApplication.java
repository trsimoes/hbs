package pt.eden.hbs.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@ComponentScan("pt.eden.hbs")
public class ServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class);
    }
}