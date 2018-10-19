package pt.eden.hbs.bank.edenred;

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
public class EdenredBankServiceImpl extends AbstractBankService<EdenredSnapshot> implements EdenredBankService {

    private static final Logger LOG = LoggerFactory.getLogger(EdenredBankServiceImpl.class);

    private static final String BASE_URL = "https://www.myedenred.pt/euroticket/";

    @Override
    protected String getLoginUrl() {
        return BASE_URL + "pages/login.jsf";
    }

    @Override
    public EdenredSnapshot getCurrentDetails() throws BankException {
        LOG.warn("Edenred balance verification is temporarily disabled. Due to site reconstruction, this verification " +
                "needs to be rebuilt.");
        EdenredSnapshot response = new EdenredSnapshot();
        response.setAccountBalance((float) 0);
        response.setCreateDateTime(LocalDateTime.now());
        return response;
    }

    @Override
    protected Map<String, String> prepareFormPostAction(final Document document) throws IOException {
        Element loginform = document.getElementById("loginform");
        Elements inputElements = loginform.getElementsByTag("input");
        Map<String, String> formParameters = new HashMap<>();
        for (Element inputElement : inputElements) {
            String key = inputElement.attr("name");
            String value = inputElement.attr("value");

            if (key.equals("loginform:username"))
                value = this.configurations.get("bank.edenred.username");
            else if (key.equals("loginform:password"))
                value = this.configurations.get("bank.edenred.password");

            formParameters.put(key, value);
        }
        return formParameters;
    }

    @Override
    protected EdenredSnapshot fetchResult() throws IOException, BankException {
        final Document document = executeRequest(BASE_URL + "pages/private/customer/customer.jsf").parse();
        final String xpath = "/html/body/table[1]/tbody/tr[3]/td/div/table/tbody/tr[2]/td[1]/table/tbody/tr/td[2]";
        final String elementRaw = Xsoup.compile(xpath).evaluate(document).get();
        final String availableBalanceRaw = elementRaw.replace("<td>", "").replace("</td>", "");
        final Float availableBalance = convert("availableBalance", availableBalanceRaw, "€");
        EdenredSnapshot response = new EdenredSnapshot();
        response.setAccountBalance(availableBalance);
        response.setCreateDateTime(LocalDateTime.now());
        return response;
    }
}
