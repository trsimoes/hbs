package pt.eden.hbs.updater;

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
import pt.eden.hbs.bank.edenred.EdenredBankService;
import pt.eden.hbs.bank.edenred.EdenredSnapshot;
import pt.eden.hbs.bank.exceptions.BankException;
import pt.eden.hbs.bank.santander.SantanderBankService;
import pt.eden.hbs.bank.santander.SantanderSnapshot;
import pt.eden.hbs.common.entity.Snapshot;
import pt.eden.hbs.configuration.ApplicationConfigurations;

import java.time.LocalDateTime;

@SpringBootApplication
@ComponentScan("pt.eden.hbs")
public class UpdaterApplication {

    private static final Logger LOG = LoggerFactory.getLogger(UpdaterApplication.class);

    private final ApplicationConfigurations configurations;

    private final SantanderBankService santanderBankService;

    private final EdenredBankService edenredBankService;

    @Autowired
    public UpdaterApplication(ApplicationConfigurations configurations, SantanderBankService santanderBankService, EdenredBankService edenredBankService) {
        this.configurations = configurations;
        this.santanderBankService = santanderBankService;
        this.edenredBankService = edenredBankService;
    }

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
                LOG.info("Getting Santander balance...");
                final SantanderSnapshot santanderSnapshot = this.santanderBankService.getCurrentDetails();
                if (santanderSnapshot != null) {
                    LOG.info("Current Balance at " + santanderSnapshot.getCreateDateTime() + " is:");
                    LOG.info("\tAccount Balance:\t" + santanderSnapshot.getAccountBalance());
                    LOG.info("\tCredit Balance:\t\t" + santanderSnapshot.getCreditBalance());
                } else {
                    LOG.warn("Could not fetch current balance.");
                }

                LOG.info("Getting Edenred balance...");
                final EdenredSnapshot edenredSnapshot = this.edenredBankService.getCurrentDetails();
                if (edenredSnapshot != null) {
                    LOG.info("Current Balance at " + edenredSnapshot.getCreateDateTime() + " is:");
                    LOG.info("\tAccount Balance:\t" + edenredSnapshot.getAccountBalance());
                }

                LOG.info("Publishing results...");
                final Snapshot snapshot = convert(santanderSnapshot, edenredSnapshot);
                final String baseURL = this.configurations.get("hbs.server.url");
                restTemplate.postForEntity(baseURL + "/send/snapshot", snapshot, Snapshot.class);
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

    private Snapshot convert(final SantanderSnapshot santanderSnapshot, final EdenredSnapshot edenredSnapshot) {
        final Snapshot snapshot = new Snapshot();
        snapshot.setCreateDateTime(LocalDateTime.now());
        snapshot.setAccountBalance(santanderSnapshot.getAccountBalance());
        snapshot.setCreditBalance(santanderSnapshot.getCreditBalance());
        snapshot.setEuroticketBalance(edenredSnapshot.getAccountBalance());
        return snapshot;
    }
}