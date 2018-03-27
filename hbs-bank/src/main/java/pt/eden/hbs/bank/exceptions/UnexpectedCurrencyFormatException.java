package pt.eden.hbs.bank.exceptions;

/**
 * @author : trsimoes
 */
public class UnexpectedCurrencyFormatException extends BankException {

    public UnexpectedCurrencyFormatException(String attribute, String value) {
        super("Unexpected format found for attribute '" + attribute + "' with value '" + value + "'. Expected value "
                + "should follow this pattern: 'xxx.xxx,xx EUR' ");
    }
}
