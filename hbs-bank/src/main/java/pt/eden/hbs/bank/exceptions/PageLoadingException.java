package pt.eden.hbs.bank.exceptions;

/**
 * @author : trsimoes
 */
public class PageLoadingException extends BankException {

    public PageLoadingException(String url, Throwable e) {
        super("Could not load page '" + url + "'.", e);
    }
}
