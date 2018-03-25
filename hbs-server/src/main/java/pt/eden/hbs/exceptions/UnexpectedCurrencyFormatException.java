package pt.eden.hbs.exceptions;

/**
 * @author : trsimoes
 */
public class UnexpectedCurrencyFormatException extends HomeBankingException {

    public UnexpectedCurrencyFormatException(String attribute, String value) {
        super("Unexpected format found for attribute '" + attribute + "' with value '" + value + "'. Expected value "
                + "should follow this pattern: 'xxx.xxx,xx EUR' ");
    }
}
