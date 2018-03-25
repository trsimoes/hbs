package pt.eden.hbs.server.services;

import pt.eden.hbs.server.entity.Snapshot;
import pt.eden.hbs.server.exceptions.HomeBankingException;

/*
 * @author : trsimoes
 */
public interface HomeBankingService {

    Snapshot getCurrentDetails() throws HomeBankingException;
}
