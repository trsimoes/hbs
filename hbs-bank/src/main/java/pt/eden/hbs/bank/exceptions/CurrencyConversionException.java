package pt.eden.hbs.bank.exceptions;

/**
 * @author : trsimoes
 */
public class CurrencyConversionException extends HomeBankingException {

    public CurrencyConversionException(String attribute, String value) {
        super("Could not convert attribute '" + attribute + "' with value '" + value + "' to a number.");
    }
}
