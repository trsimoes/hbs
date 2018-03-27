package pt.eden.hbs.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import pt.eden.hbs.configuration.ApplicationConfigurations;

@SpringBootApplication
//@EnableScheduling
@ComponentScan("pt.eden.hbs")
public class ServerApplication {

    private static final Logger LOG = LoggerFactory.getLogger(ServerApplication.class);

    @Autowired
    @SuppressWarnings("unused")
    private ApplicationConfigurations configurations;

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class);
    }
}