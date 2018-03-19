package pt.eden.hbs.services;

import pt.eden.hbs.entity.Snapshot;
import pt.eden.hbs.exceptions.HomeBankingException;

/*
 * @author : trsimoes
 */
public interface HomeBankingService {

    Snapshot getCurrentDetails() throws HomeBankingException;
}
