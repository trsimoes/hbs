package pt.eden.hbs.bank;

import pt.eden.hbs.exceptions.CurrencyException;

/*
 * @author : trsimoes
 */
public interface HomeBankingService {

    HomeBankingSnapshot getCurrentDetails() throws CurrencyException;
}
