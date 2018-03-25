package pt.eden.hbs.updater;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import pt.eden.hbs.bank.Context;
import pt.eden.hbs.bank.HomeBankingService;
import pt.eden.hbs.bank.HomeBankingServiceFactory;
import pt.eden.hbs.bank.Snapshot;
import pt.eden.hbs.bank.exceptions.HomeBankingException;
import pt.eden.hbs.configuration.ApplicationConfigurations;

import java.io.File;

@SpringBootApplication
public class UpdaterApplication {

    private static final Logger LOG = LoggerFactory.getLogger(UpdaterApplication.class);

    private final ApplicationConfigurations configurations = ApplicationConfigurations.getInstance();

    public static void main(String args[]) {
        SpringApplication.run(UpdaterApplication.class);
    }

    @Bean
    @SuppressWarnings("unused")
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    @SuppressWarnings("unused")
    public CommandLineRunner run(RestTemplate restTemplate) {
        return args -> {
            try {
                initializeWebDriver();
                Snapshot snapshot = fetchHomeBankSnapshot();
                restTemplate.postForEntity("http://localhost:8080/send/snapshot", snapshot, Snapshot.class);
            } catch (HomeBankingException e) {
                LOG.error("Error getting home bank details", e);
            }
        };
    }

    private Snapshot fetchHomeBankSnapshot() throws HomeBankingException {
        final HomeBankingServiceFactory homeBankingServiceFactory = HomeBankingServiceFactory.getInstance();
        final String username = this.configurations.get("home.banking.username");
        final String password = this.configurations.get("home.banking.password");
        Context context = new Context(username, password);
        final HomeBankingService homeBankingService = homeBankingServiceFactory.get(context);
        return homeBankingService.getCurrentDetails();
    }

    private void initializeWebDriver() {
        final String driverPath = this.configurations.get("webdriver.gecko.driver");
        if (!new File(driverPath).isFile()) {
            LOG.error("The 'webdriver.gecko.driverPath' property points to an invalid " + "location: " + driverPath
                    + ". Please verify the driver path.");
        } else if (StringUtils.isNotBlank(driverPath)) {
            System.setProperty("webdriver.gecko.driver", driverPath);
        } else {
            LOG.error(
                    "The 'webdriver.gecko.driverPath' property is not correctly specified in the 'hbs.properties' file.");
            System.exit(-1);
        }
    }
}