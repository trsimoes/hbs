package hello;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * @author : trsimoes
 */
public class SeleniumTest {
    public static void main(String[] args) throws IOException {
        System.setProperty("webdriver.gecko.driver", "C:\\programs\\geckodriver.exe");
        SeleniumTest app = new SeleniumTest();

        FirefoxBinary firefoxBinary = new FirefoxBinary();
        firefoxBinary.addCommandLineOptions("--headless");
        FirefoxOptions firefoxOptions = new FirefoxOptions();
//        firefoxOptions.setBinary(firefoxBinary);
        WebDriver driver = new FirefoxDriver(firefoxOptions);

        //        app.test(driver);
        app.bank(driver);
    }

    private void bank(WebDriver driver) throws IOException {
        try {
            driver.navigate().to("https://www.particulares.santandertotta.pt/bepp/sanpt/usuarios/login/?");
            WebElement userName_editbox = driver.findElement(By.id("identificacionUsuario"));
            WebElement password_editbox = driver.findElement(By.id("claveConsultiva"));
            WebElement submit_button = driver.findElement(By.xpath("//a[@id='login_button']"));
            userName_editbox.sendKeys("xxx");
            password_editbox.sendKeys("xxx");
            submit_button.click();

            driver.navigate().to(
                    "https://www.particulares.santandertotta.pt/bepp/sanpt/cuentas/listadomovimientoscuenta/0,,,0.shtml?trxId=201803180025635064");
            String accountBalance = driver.findElement(
                    By.xpath("/html/body/form[3]/div[7]/div[2]/table/tbody/tr/td[4]/span")).getText();

            driver.navigate().to(
                    "https://www.particulares.santandertotta.pt/bepp/sanpt/tarjetas/posiciontarjetas/0,,,0.shtml?bf=9&nuevo=si&trxId=201803180025635545");
            String creditLimit = driver.findElement(By.xpath("/html/body/form[2]/div[3]/table[1]/tbody/tr[4]/td[3]"))
                    .getText();
            String remainingCredit = driver.findElement(
                    By.xpath("/html/body/form[2]/div[3]/table[1]/tbody/tr[4]/td[4]")).getText();

            System.out.println("-- Account ---");
            System.out.println("\tBalance: " + accountBalance);     // 100,05 EUR
            System.out.println("--- Card ---");
            System.out.println("\tLimit: " + creditLimit);          // 1.000,00 EUR
            System.out.println("\tRemaining: " + remainingCredit);

            driver.navigate().to("https://www.particulares.santandertotta.pt//bepp/sanpt/usuarios/desconexion/0,,,"
                    + "0.shtml?trxId=201803180025637992");
            WebElement logoutButton = driver.findElement(By.xpath("//*[@id=\"exit\"]"));
            logoutButton.click();
        } finally {
            driver.close();
        }
    }

    private void test(WebDriver driver) throws IOException {

        // openTestSite
        driver.navigate().to("http://testing-ground.scraping.pro/login");

        // login
        WebElement userName_editbox = driver.findElement(By.id("usr"));
        WebElement password_editbox = driver.findElement(By.id("pwd"));
        WebElement submit_button = driver.findElement(By.xpath("//input[@value='Login']"));
        userName_editbox.sendKeys("admin");
        password_editbox.sendKeys("12345");
        submit_button.click();

        // getText
        String text = driver.findElement(By.xpath("//div[@id='case_login']/h3")).getText();
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("status.txt"), "utf-8"))) {
            writer.write(text);
        }

        // saveScreenshot
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile, new File("screenshot.png"));

        // closeBrowser
        driver.close();
    }

}
