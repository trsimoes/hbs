package pt.eden.hbs.bank.exceptions;

/**
 * @author : trsimoes
 */
public abstract class BankException extends Exception {

    BankException(final String message) {
        super(message);
    }

    BankException(final String message, final Throwable e) {
        super(message, e);
    }
}
