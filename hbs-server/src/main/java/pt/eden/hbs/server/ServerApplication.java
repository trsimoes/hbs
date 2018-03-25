package pt.eden.hbs.server;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pt.eden.hbs.server.conf.ApplicationConfigurations;

import java.io.File;

@SpringBootApplication
//@EnableScheduling
public class ServerApplication {

    private static final Logger LOG = LoggerFactory.getLogger(ServerApplication.class);

    public static void main(String[] args) {

        final ApplicationConfigurations configurations = ApplicationConfigurations.getInstance();
        final String driverPath = configurations.get("webdriver.gecko.driver");
        if (!new File(driverPath).isFile()) {
            LOG.error("The 'webdriver.gecko.driverPath' property points to an invalid " + "location: " + driverPath
                    + ". Please verify the driver path.");
        }

        if (StringUtils.isNotBlank(driverPath)) {
            System.setProperty("webdriver.gecko.driver", driverPath);
        } else
            LOG.error(
                    "The 'webdriver.gecko.driverPath' property is not correctly specified in the 'hbs.properties' file.");

        SpringApplication.run(ServerApplication.class, args);
    }
}