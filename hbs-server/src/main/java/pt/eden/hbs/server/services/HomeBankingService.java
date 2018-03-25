package pt.eden.hbs.server.services;

import pt.eden.hbs.server.entity.SnapshotEntity;
import pt.eden.hbs.server.exceptions.HomeBankingException;

/*
 * @author : trsimoes
 */
public interface HomeBankingService {

    SnapshotEntity getCurrentDetails() throws HomeBankingException;
}
