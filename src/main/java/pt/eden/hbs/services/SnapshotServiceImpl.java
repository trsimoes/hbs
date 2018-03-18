package pt.eden.hbs.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.eden.hbs.entity.Snapshot;
import pt.eden.hbs.persistence.SnapshotRepository;

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

        if (log.isDebugEnabled()) {
            log.debug("Getting details from Home Banking server");
        }

        final Snapshot snapshot = this.homeBankingService.getCurrentDetails();

        if (log.isTraceEnabled()) {
            log.trace("Details from Bank: " + snapshot.toString());
        }

        snapshot.setCreateDateTime(LocalDateTime.now());

        if (log.isTraceEnabled()) {
            log.trace("Saving snapshot to database: " + snapshot.toString());
        }

        this.snapshotRepository.save(snapshot);
    }
}