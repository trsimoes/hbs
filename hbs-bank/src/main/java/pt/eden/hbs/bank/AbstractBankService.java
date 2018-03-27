package pt.eden.hbs.bank;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import pt.eden.hbs.bank.exceptions.BankException;
import pt.eden.hbs.configuration.ApplicationConfigurations;

import javax.annotation.PostConstruct;
import java.io.File;

/**
 * @author : trsimoes
 */
public abstract class AbstractBankService<T extends AbstractSnapshot> implements BankService<T> {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractBankService.class);

    @Autowired
    @SuppressWarnings("unused")
    protected ApplicationConfigurations configurations;

    protected WebDriver driver;

    @PostConstruct
    @SuppressWarnings("unused")
    public void setupDriver() {

        final String driverPath = this.configurations.get("webdriver.gecko.driver");
        if (!new File(driverPath).isFile()) {
            LOG.error("The 'webdriver.gecko.driverPath' property points to an invalid " + "location: " + driverPath
                    + ". Please verify the driver path.");
        } else if (StringUtils.isNotBlank(driverPath)) {
            System.setProperty("webdriver.gecko.driver", driverPath);
        } else
            LOG.error(
                    "The 'webdriver.gecko.driverPath' property is not correctly specified in the 'hbs.properties' file.");

        FirefoxBinary firefoxBinary = new FirefoxBinary();
        firefoxBinary.addCommandLineOptions("--headless");
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.setBinary(firefoxBinary);
        this.driver = new FirefoxDriver(firefoxOptions);
    }

    @Override
    public T getCurrentDetails() throws BankException {
        try {
            login();
            T response = execute();
            logout();
            return response;
        } finally {
            if (this.driver != null) {
                try {
                    this.driver.quit();
                } catch (Exception e) {
                    LOG.warn("Could not quit web driver: " + e.getLocalizedMessage());
                    try {
                        this.driver.close();
                    } catch (Exception e1) {
                        LOG.warn("Could not close web driver: " + e.getLocalizedMessage());
                    }
                }
            }
        }
    }

    public abstract void login();

    public abstract void logout();

    public abstract T execute() throws BankException;
}
