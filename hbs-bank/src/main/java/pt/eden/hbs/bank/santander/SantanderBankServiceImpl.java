package pt.eden.hbs.bank.santander;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pt.eden.hbs.bank.AbstractBankService;
import pt.eden.hbs.bank.exceptions.BankException;
import us.codecraft.xsoup.Xsoup;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : trsimoes
 */
@Service
@SuppressWarnings("unused")
public class SantanderBankServiceImpl extends AbstractBankService<SantanderSnapshot> implements SantanderBankService {

    private static final Logger LOG = LoggerFactory.getLogger(SantanderBankServiceImpl.class);

    private static final String BASE_URL = "https://www.particulares.santandertotta.pt/";

    @Override
    protected String getLoginUrl() {
        return BASE_URL + "bepp/sanpt/usuarios/login/?";
    }

    @Override
    protected Map<String, String> prepareFormPostAction(final Document document) throws IOException {
        Element loginForm = document.getElementsByTag("form").get(0);
        Elements inputElements = loginForm.getElementsByTag("input");
        Map<String, String> formParameters = new HashMap<>();
        for (Element inputElement : inputElements) {
            String key = inputElement.attr("name");
            String value = inputElement.attr("value");

            if (key.equals("identificacionUsuario"))
                value = this.configurations.get("bank.santander.username");
            else if (key.equals("claveConsultiva"))
                value = this.configurations.get("bank.santander.password");
            formParameters.put(key, value);
        }
        // hidden OGC_TOKEN
        String url = "https://www.particulares.santandertotta.pt/nbp_guard";
        Document authTokenDocument = Jsoup.connect(url).header("FETCH-CSRF-TOKEN", "1").post();
        String token = authTokenDocument.body().html();
        final String[] split = token.split(":");
        formParameters.put(split[0], split[1]);
        return formParameters;
    }

    @Override
    protected SantanderSnapshot fetchResult() throws IOException, BankException {
        // <span class="money">164,48 EUR</span>
        final SantanderSnapshot snapshot = new SantanderSnapshot();

        // account
        Document document = executeRequest(BASE_URL + "/bepp/sanpt/cuentas/listadomovimientoscuenta/0,,,0.shtml").parse();
        String xpath = "/html/body/form[3]/div[7]/div[2]/table/tbody/tr/td[4]/span";
        String elementRaw = Xsoup.compile(xpath).evaluate(document).get();
        String accountBalanceRaw = elementRaw.replace("<span class=\"money\">", "").replace("</span>", "");
        final Float accountBalance = convert("accountBalance", accountBalanceRaw, "EUR");
        snapshot.setAccountBalance(accountBalance);

        // card
        document = executeRequest(BASE_URL + "/bepp/sanpt/tarjetas/posiciontarjetas/0,,,0.shtml?bf=9&nuevo=si").parse();
        // <td class="money">1.500,00 EUR</td>
        xpath = "/html/body/form[2]/div[3]/table[1]/tbody/tr[4]/td[3]";
        elementRaw = Xsoup.compile(xpath).evaluate(document).get();
        String creditLimitRaw = elementRaw.replace("<td class=\"money\">", "").replace("</td>", "");
        final Float creditLimit = convert("accountBalance", creditLimitRaw, "EUR");

        // <td class="money" style="border-right: 1px solid rgb(255, 255, 255);">754,92 EUR</td>
        xpath = "/html/body/form[2]/div[3]/table[1]/tbody/tr[4]/td[4]";
        elementRaw = Xsoup.compile(xpath).evaluate(document).get();
        String remainingCreditRaw = elementRaw.replace("<td class=\"money\" style=\"border-right: 1px solid rgb(255,"
                + " 255, 255);\">", "").replace("</td>", "");
        final Float remainingCredit = convert("remainingCredit", remainingCreditRaw, "EUR");

        final Float creditBalance = creditLimit - remainingCredit;
        snapshot.setCreditBalance(creditBalance);

        if (LOG.isTraceEnabled()) {
            LOG.trace("---------------------------");
            LOG.trace("Snapshot details");
            LOG.trace("\taccountBalance: " + accountBalance);
            LOG.trace("\tcreditLimit: " + creditLimit);
            LOG.trace("\tremainingCredit: " + remainingCredit);
            LOG.trace("\tcreditBalance: " + creditBalance);
            LOG.trace("---------------------------");
        }

        snapshot.setCreateDateTime(LocalDateTime.now());

        return snapshot;
    }
}
