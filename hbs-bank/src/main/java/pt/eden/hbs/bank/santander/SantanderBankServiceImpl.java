package pt.eden.hbs.bank.santander;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.eden.hbs.bank.AbstractBankService;
import pt.eden.hbs.bank.exceptions.BankException;
import pt.eden.hbs.bank.exceptions.CurrencyConversionException;
import pt.eden.hbs.bank.exceptions.UnexpectedCurrencyFormatException;
import pt.eden.hbs.configuration.ApplicationConfigurations;

import java.time.LocalDateTime;

/**
 * @author : trsimoes
 */
@Service
@SuppressWarnings("unused")
public class SantanderBankServiceImpl extends AbstractBankService<SantanderSnapshot> implements SantanderBankService {

    private static final Logger log = LoggerFactory.getLogger(SantanderBankServiceImpl.class);

    private static final String BASE_URL = "https://www.particulares.santandertotta.pt/";

    @Autowired
    @SuppressWarnings("unused")
    private ApplicationConfigurations configurations;

    @Override
    public void logout() {
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

    @Override
    public void login() {
        try {
            if (log.isTraceEnabled()) {
                log.trace("Login - start");
            }

            String loginURL = BASE_URL + "/bepp/sanpt/usuarios/login/?";
            driver.navigate().to(loginURL);
            WebElement usernameText = driver.findElement(By.id("identificacionUsuario"));
            WebElement passwordText = driver.findElement(By.id("claveConsultiva"));
            WebElement submitButton = driver.findElement(By.xpath("//a[@id='login_button']"));

            usernameText.sendKeys(this.configurations.get("home.banking.username"));
            passwordText.sendKeys(this.configurations.get("home.banking.password"));
            submitButton.click();

        } finally {
            if (log.isTraceEnabled()) {
                log.trace("Login - end");
            }
        }
    }

    @Override
    public SantanderSnapshot execute() throws BankException {
        try {
            if (log.isTraceEnabled()) {
                log.trace("Fetch Information - start");
            }
            final SantanderSnapshot snapshot = new SantanderSnapshot();

            // account
            driver.navigate().to(
                    BASE_URL + "/bepp/sanpt/cuentas/listadomovimientoscuenta/0,,,0.shtml?trxId=201803180025635064");
            final String accountBalanceRaw = driver.findElement(
                    By.xpath("/html/body/form[3]/div[7]/div[2]/table/tbody/tr/td[4]/span")).getText();
            final Float accountBalance = convert("accountBalance", accountBalanceRaw);
            snapshot.setAccountBalance(accountBalance);

            // card
            driver.navigate().to(BASE_URL
                    + "/bepp/sanpt/tarjetas/posiciontarjetas/0,,,0.shtml?bf=9&nuevo=si&trxId=201803180025635545");
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

            snapshot.setCreateDateTime(LocalDateTime.now());

            return snapshot;
        } finally {
            if (log.isTraceEnabled()) {
                log.trace("Fetch Information - end");
            }
        }
    }

    private Float convert(final String attribute, final String value) throws BankException {
        if (StringUtils.isNotBlank(value)) {
            if (value.trim().endsWith(" EUR")) {
                String tmp = value.trim();
                tmp = tmp.replace(".", "");
                tmp = tmp.replace(",", ".");
                tmp = tmp.substring(0, tmp.length() - 4);
                return Float.valueOf(tmp);
            } else {
                throw new UnexpectedCurrencyFormatException(attribute, value);
            }
        } else {
            throw new CurrencyConversionException(attribute, value);
        }
    }
}
