package pt.eden.hbs.server;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import pt.eden.hbs.configuration.ApplicationConfigurations;

import java.io.File;

@SpringBootApplication
//@EnableScheduling
@ComponentScan("pt.eden.hbs")
public class ServerApplication {

    private static final Logger LOG = LoggerFactory.getLogger(ServerApplication.class);

    @Autowired
    @SuppressWarnings("unused")
    private ApplicationConfigurations configurations;

    @Bean
    @SuppressWarnings("unused")
    public CommandLineRunner run() {
        return args -> {
            final String driverPath = this.configurations.get("webdriver.gecko.driver");
            if (!new File(driverPath).isFile()) {
                LOG.error("The 'webdriver.gecko.driverPath' property points to an invalid " + "location: " + driverPath
                        + ". Please verify the driver path.");
            } else if (StringUtils.isNotBlank(driverPath)) {
                System.setProperty("webdriver.gecko.driver", driverPath);
            } else
                LOG.error(
                        "The 'webdriver.gecko.driverPath' property is not correctly specified in the 'hbs.properties' file.");
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class);
    }
}