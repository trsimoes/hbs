package pt.eden.hbs.bank.edenred;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pt.eden.hbs.bank.AbstractBankService;
import pt.eden.hbs.bank.exceptions.BankException;

import java.time.LocalDateTime;

/**
 * @author : trsimoes
 */
@Service
public class EdenredBankServiceImpl extends AbstractBankService<EdenredSnapshot> implements EdenredBankService {

    private static final Logger LOG = LoggerFactory.getLogger(EdenredBankServiceImpl.class);

    private static final String BASE_URL = "https://www.myedenred.pt/euroticket";

    @Override
    public void login() {
        String loginURL = BASE_URL + "/pages/login.jsf";
        driver.navigate().to(loginURL);
        WebElement usernameText = driver.findElement(By.id("loginform:username"));
        WebElement passwordText = driver.findElement(By.id("loginform:password"));
        WebElement submitButton = driver.findElement(By.id("loginform:loginButton"));

        usernameText.sendKeys(this.configurations.get("bank.edenred.username"));
        passwordText.sendKeys(this.configurations.get("bank.edenred.password"));
        submitButton.click();
    }

    @Override
    public void logout() {
        driver.navigate().to(BASE_URL + "/pages/private/customer/customer.jsf?windowId=da0");
        WebElement logoutButton = driver.findElement(By.id("headerForm:logoutBtn"));
        logoutButton.click();
    }

    @Override
    public EdenredSnapshot execute() throws BankException {
        driver.navigate().to(BASE_URL + "/pages/private/customer/customer.jsf?windowId=da0");
        final String availableBalanceRaw = driver.findElement(
                By.xpath("/html/body/table[1]/tbody/tr[3]/td/div/table/tbody/tr[2]/td[1]/table/tbody/tr/td[2]"))
                .getText();
        final Float availableBalance = convert("availableBalance", availableBalanceRaw);
        EdenredSnapshot response = new EdenredSnapshot();
        response.setAccountBalance(availableBalance);
        response.setCreateDateTime(LocalDateTime.now());
        return response;
    }

    @Override
    public String amountCurrencySuffix() {
        return "€";
    }
}
