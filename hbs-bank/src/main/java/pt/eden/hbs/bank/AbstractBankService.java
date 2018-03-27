package pt.eden.hbs.bank;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import pt.eden.hbs.bank.exceptions.BankException;

import javax.annotation.PostConstruct;

/**
 * @author : trsimoes
 */
public abstract class AbstractBankService<T extends AbstractSnapshot> implements BankService<T> {

    protected WebDriver driver;

    @PostConstruct
    public void setupDriver() {
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
                this.driver.quit();
            }
        }
    }

    public abstract void login();

    public abstract void logout();

    public abstract T execute() throws BankException;
}
