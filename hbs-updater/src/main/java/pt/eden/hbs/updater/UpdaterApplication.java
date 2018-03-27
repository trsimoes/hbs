package pt.eden.hbs.updater;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import pt.eden.hbs.bank.exceptions.BankException;
import pt.eden.hbs.bank.santander.SantanderBankService;
import pt.eden.hbs.bank.santander.SantanderSnapshot;
import pt.eden.hbs.configuration.ApplicationConfigurations;

import java.io.File;

@SpringBootApplication
@ComponentScan("pt.eden.hbs")
public class UpdaterApplication {

    private static final Logger LOG = LoggerFactory.getLogger(UpdaterApplication.class);

    @Autowired
    @SuppressWarnings("unused")
    private ApplicationConfigurations configurations;

    @Autowired
    @SuppressWarnings("unused")
    private SantanderBankService santanderBankService;

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
                LOG.info("Getting current balance...");
                initializeWebDriver();
                final SantanderSnapshot snapshot = this.santanderBankService.getCurrentDetails();
                if (snapshot != null) {
                    LOG.info("Current Balance at " + snapshot.getCreateDateTime() + " is:");
                    LOG.info("\tAccount Balance:\t" + snapshot.getAccountBalance());
                    LOG.info("\tCredit Balance:\t\t" + snapshot.getCreditBalance());
                } else {
                    LOG.warn("Could not fetch current balance.");
                }
                final String baseURL = this.configurations.get("hbs.server.url");
                restTemplate.postForEntity(baseURL + "/send/snapshot", snapshot, SantanderSnapshot.class);
                LOG.info("Details successfully stored in HBS database");
                LOG.info("For more information, please visit: http://tsns1.dtdns.net:8080/chart.html");
            } catch (BankException e) {
                LOG.error("Error getting home bank details", e);
            } catch (RestClientException e) {
                LOG.error("Error storing snapshot on HBS server", e);
            } catch (Throwable e) {
                LOG.error("Unexpected error was caught", e);
            }
        };
    }

    private void initializeWebDriver() {
        final String driverPath = this.configurations.get("webdriver.gecko.driver");
        if (!new File(driverPath).isFile()) {
            LOG.error("The 'webdriver.gecko.driverPath' property points to an invalid " + "location: " + driverPath
                    + ". Please verify the driver path.");
            System.exit(-1);
        } else if (StringUtils.isNotBlank(driverPath)) {
            System.setProperty("webdriver.gecko.driver", driverPath);
        } else {
            LOG.error(
                    "The 'webdriver.gecko.driverPath' property is not correctly specified in the 'hbs.properties' file.");
            System.exit(-1);
        }
    }
}