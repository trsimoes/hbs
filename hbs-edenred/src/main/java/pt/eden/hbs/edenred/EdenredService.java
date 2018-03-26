package pt.eden.hbs.edenred;

import pt.eden.hbs.exceptions.CurrencyException;

/*
 * @author : trsimoes
 */
public interface EdenredService {

    EdenredSnapshot getCurrentDetails() throws CurrencyException;
}
