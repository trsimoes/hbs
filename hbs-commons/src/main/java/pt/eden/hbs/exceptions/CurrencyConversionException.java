package pt.eden.hbs.exceptions;

/**
 * @author : trsimoes
 */
public class CurrencyConversionException extends CurrencyException {

    public CurrencyConversionException(String attribute, String value) {
        super("Could not convert attribute '" + attribute + "' with value '" + value + "' to a number.");
    }
}
