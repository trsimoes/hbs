package pt.eden.hbs.exceptions;

/**
 * @author : trsimoes
 */
public abstract class CurrencyException extends Exception {
    CurrencyException(final String message) {
        super(message);
    }
}
