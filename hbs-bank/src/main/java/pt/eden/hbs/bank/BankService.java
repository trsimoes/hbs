package pt.eden.hbs.bank;

import pt.eden.hbs.bank.exceptions.BankException;

/*
 * @author : trsimoes
 */
public interface BankService<T extends AbstractSnapshot> {

    T getCurrentDetails() throws BankException;
}
