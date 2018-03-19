package pt.eden.hbs;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import pt.eden.hbs.conf.ApplicationConfigurations;

import java.io.File;

@SpringBootApplication
@EnableScheduling
public class Application {
    public static void main(String[] args) {
        final ApplicationConfigurations configurations = ApplicationConfigurations.getInstance();
        final String driverPath = configurations.get("webdriver.gecko.driverPath");

        if (!new File(driverPath).isFile()) {
            throw new IllegalArgumentException(
                    "The 'webdriver.gecko.driverPath' property points to an invalid " + "location: " + driverPath
                            + ". Please verify the driver path.");
        }

        if (StringUtils.isNotBlank(driverPath)) {
            System.setProperty("webdriver.gecko.driverPath", driverPath);
        } else {
            throw new IllegalArgumentException("The 'webdriver.gecko.driverPath' property is not correctly specified "
                    + "in the 'hbs.properties' file.");
        }

        SpringApplication.run(Application.class, args);
    }
}