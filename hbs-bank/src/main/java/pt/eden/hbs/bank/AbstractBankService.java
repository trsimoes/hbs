package pt.eden.hbs.bank;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import pt.eden.hbs.bank.exceptions.BankException;
import pt.eden.hbs.bank.exceptions.CurrencyConversionException;
import pt.eden.hbs.bank.exceptions.PageLoadingException;
import pt.eden.hbs.bank.exceptions.UnexpectedCurrencyFormatException;
import pt.eden.hbs.configuration.ApplicationConfigurations;

import java.io.IOException;
import java.util.Map;

/**
 * @author : trsimoes
 */
public abstract class AbstractBankService<T extends AbstractSnapshot> implements BankService<T> {

    private Map<String, String> cookies = null;

    @Autowired
    protected ApplicationConfigurations configurations;

    @Override
    public T getCurrentDetails() throws BankException {
        try {
            final Document document = executeRequest(getLoginUrl()).parse();
            final Map<String, String> formParameters = prepareFormPostAction(document);
            // authentication form post action
            executeRequest(getLoginUrl(), formParameters);
            return fetchResult();
        } catch (Throwable e) {
            throw new PageLoadingException("Error getting current details", e);
        }
    }

    protected abstract T fetchResult() throws IOException, BankException;

    protected abstract String getLoginUrl();

    protected abstract Map<String, String> prepareFormPostAction(final Document document) throws IOException;

    private Connection buildConnection(final String url, final Map<String, String> parameters) {
        Connection connection = Jsoup.connect(url)
                .userAgent("Mozilla/5.0")
                .timeout(10 * 1000)
                .followRedirects(true);
        if (this.cookies != null) {
            connection = connection.cookies(this.cookies);
        }
        if (parameters != null) {
            connection.data(parameters);
        }
        return connection;
    }

    protected Response executeRequest(final String url) throws IOException {
        return executeRequest(url, null);
    }

    private Response executeRequest(final String url, final Map<String, String> parameters) throws IOException {
        final Connection connection = buildConnection(url, parameters);
        Response response = connection.execute();
        if (response != null && response.cookies() != null && response.cookies().size() > 0) {
            this.cookies = response.cookies();
        }
        return response;
    }

    protected Float convert(final String attribute, final String value, final String currencySuffix) throws
            BankException {
        if (StringUtils.isNotBlank(value)) {
            if (value.trim().endsWith(" " + currencySuffix)) {
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
