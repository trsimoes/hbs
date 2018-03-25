package pt.eden.hbs.bank;

import pt.eden.hbs.bank.exceptions.HomeBankingException;

/*
 * @author : trsimoes
 */
public interface HomeBankingService {

    Snapshot getCurrentDetails() throws HomeBankingException;
}
