package pt.eden.hbs.services;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxDriverLogLevel;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pt.eden.hbs.conf.ApplicationConfigurations;
import pt.eden.hbs.entity.Snapshot;
import pt.eden.hbs.exceptions.CurrencyConversionException;
import pt.eden.hbs.exceptions.HomeBankingException;
import pt.eden.hbs.exceptions.UnexpectedCurrencyFormatException;

import java.io.File;

/**
 * @author : trsimoes
 */
@Service
public class HomeBankingServiceImpl implements HomeBankingService {

    private static final Logger log = LoggerFactory.getLogger(HomeBankingServiceImpl.class);

    private static final String BASE_URL = "https://www.particulares.santandertotta.pt/";

    @Override
    public Snapshot getCurrentDetails() throws HomeBankingException {
        try {
            if (log.isTraceEnabled()) {
                log.trace("Get current details - start");
            }
            WebDriver driver = null;
            try {
                driver = login();
                return fetchInformation(driver);
            } finally {
                logout(driver);
            }
        } finally {
            if (log.isTraceEnabled()) {
                log.trace("Get current details - end");
            }
        }
    }

    private void logout(final WebDriver driver) {
        try {
            if (log.isTraceEnabled()) {
                log.trace("Logout - start");
            }
            driver.navigate().to(BASE_URL + "/bepp/sanpt/usuarios/desconexion/0,,,0.shtml?trxId=201803180025637992");
            WebElement logoutButton = driver.findElement(By.xpath("//*[@id=\"exit\"]"));
            logoutButton.click();
        } finally {
            if (driver != null) {
                driver.close();
            }
            if (log.isTraceEnabled()) {
                log.trace("Logout - end");
            }
        }
    }

    private Snapshot fetchInformation(final WebDriver driver) throws HomeBankingException {
        try {
            if (log.isTraceEnabled()) {
                log.trace("Fetch Information - start");
            }
            final Snapshot snapshot = new Snapshot();

            // account
            driver.navigate().to(
                    BASE_URL + "/bepp/sanpt/cuentas/listadomovimientoscuenta/0,,,0.shtml?trxId=201803180025635064");
            final String accountBalanceRaw = driver.findElement(
                    By.xpath("/html/body/form[3]/div[7]/div[2]/table/tbody/tr/td[4]/span")).getText();
            final Float accountBalance = convert("accountBalance", accountBalanceRaw);
            snapshot.setAccountBalance(accountBalance);

            // card
            driver.navigate().to(
                    BASE_URL + "/bepp/sanpt/tarjetas/posiciontarjetas/0,,,0.shtml?bf=9&nuevo=si&trxId=201803180025635545");
            final String creditLimitRaw = driver.findElement(
                    By.xpath("/html/body/form[2]/div[3]/table[1]/tbody/tr[4]/td[3]")).getText();
            final Float creditLimit = convert("creditLimit", creditLimitRaw);

            final String remainingCreditRaw = driver.findElement(
                    By.xpath("/html/body/form[2]/div[3]/table[1]/tbody/tr[4]/td[4]")).getText();
            final Float remainingCredit = convert("remainingCredit", remainingCreditRaw);

            final Float creditBalance = creditLimit - remainingCredit;
            snapshot.setCreditBalance(creditBalance);

            if (log.isTraceEnabled()) {
                log.trace("---------------------------");
                log.trace("Snapshot details");
                log.trace("\taccountBalance: " + accountBalance);
                log.trace("\tcreditLimit: " + creditLimit);
                log.trace("\tremainingCredit: " + remainingCredit);
                log.trace("\tcreditBalance: " + creditBalance);
                log.trace("---------------------------");
            }

            return snapshot;
        } finally {
            if (log.isTraceEnabled()) {
                log.trace("Fetch Information - end");
            }
        }
    }

    private WebDriver login() {
        try {
            if (log.isTraceEnabled()) {
                log.trace("Login - start");
            }

            final ApplicationConfigurations configurations = ApplicationConfigurations.getInstance();
            final WebDriver driver = setupDriver();

            String loginURL = BASE_URL + "/bepp/sanpt/usuarios/login/?";
            driver.navigate().to(loginURL);
            WebElement usernameText = driver.findElement(By.id("identificacionUsuario"));
            WebElement passwordText = driver.findElement(By.id("claveConsultiva"));
            WebElement submitButton = driver.findElement(By.xpath("//a[@id='login_button']"));

            usernameText.sendKeys(configurations.get("home.banking.username"));
            passwordText.sendKeys(configurations.get("home.banking.password"));
            submitButton.click();

            return driver;
        } finally {
            if (log.isTraceEnabled()) {
                log.trace("Login - end");
            }
        }
    }

    private WebDriver setupDriver() {
        try {
            if (log.isTraceEnabled()) {
                log.trace("Setup Driver - start");
            }

            FirefoxBinary firefoxBinary = new FirefoxBinary(new File(ApplicationConfigurations.getInstance().get
                    ("webdriver.gecko.driver")));
            firefoxBinary.addCommandLineOptions("--headless");
            FirefoxOptions firefoxOptions = new FirefoxOptions();
            firefoxOptions.setBinary(firefoxBinary);
            firefoxOptions.setLogLevel(FirefoxDriverLogLevel.ERROR);
            return new FirefoxDriver(firefoxOptions);
        } catch (Throwable e) {
            log.error("Error creating the web driver.", e);
        } finally {
            if (log.isTraceEnabled()) {
                log.trace("Setup Driver - end");
            }
        }
    }

    private Float convert(final String attribute, final String value) throws HomeBankingException {
        if (StringUtils.isNotBlank(value)) {
            if (value.trim().endsWith(" EUR")) {
                String tmp= value.trim();
                tmp = tmp.replace(".", "");
                tmp = tmp.replace(",", ".");
                tmp = tmp.substring(0, tmp.length()-4);
                return Float.valueOf(tmp);
            } else {
                throw new UnexpectedCurrencyFormatException(attribute, value);
            }
        } else {
            throw new CurrencyConversionException(attribute, value);
        }
    }
}
