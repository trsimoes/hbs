package pt.eden.hbs.bank.exceptions;

/**
 * @author : trsimoes
 */
public abstract class HomeBankingException extends Exception {
    HomeBankingException(final String message) {
        super(message);
    }
}
