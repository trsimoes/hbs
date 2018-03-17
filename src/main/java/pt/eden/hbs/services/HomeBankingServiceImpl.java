package pt.eden.hbs.services;

import org.springframework.stereotype.Service;
import pt.eden.hbs.entity.Snapshot;

/**
 * @author : trsimoes
 */
@Service
public class HomeBankingServiceImpl implements HomeBankingService {

    @Override
    public Snapshot getCurrentDetails() {
        Snapshot snapshot = new Snapshot();
        snapshot.setCreditBalance(generateRandomNumber(1L, 1500L));
        snapshot.setAccountBalance(generateRandomNumber(-1501L, 1501L));
        return snapshot;
    }

    private long generateRandomNumber(final long leftLimit, final long rightLimit) {
        return leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
    }
}
