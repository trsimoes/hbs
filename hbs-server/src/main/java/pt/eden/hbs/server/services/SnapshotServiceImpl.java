package pt.eden.hbs.server.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.eden.hbs.server.api.service.SnapshotService;
import pt.eden.hbs.server.entity.SnapshotEntity;
import pt.eden.hbs.server.exceptions.HomeBankingException;
import pt.eden.hbs.server.persistence.SnapshotRepository;

import java.time.LocalDateTime;

/**
 * @author : trsimoes
 */
@Service
public class SnapshotServiceImpl implements SnapshotService {

    private static final Logger log = LoggerFactory.getLogger(SnapshotServiceImpl.class);

    @Autowired
    private HomeBankingService homeBankingService;

    @Autowired
    private SnapshotRepository snapshotRepository;

    @Override
    public void takeSnapshot() {

        try {
            if (log.isDebugEnabled()) {
                log.debug("Getting details from Home Banking server");
            }

            final SnapshotEntity snapshot = this.homeBankingService.getCurrentDetails();

            if (log.isTraceEnabled()) {
                log.trace("Details from Bank: " + snapshot.toString());
            }

            snapshot.setCreateDateTime(LocalDateTime.now());

            if (log.isTraceEnabled()) {
                log.trace("Saving snapshot to database: " + snapshot.toString());
            }

            this.snapshotRepository.save(snapshot);
        } catch (HomeBankingException e) {
            log.error("Error getting snapshot", e);
        }
    }
}
