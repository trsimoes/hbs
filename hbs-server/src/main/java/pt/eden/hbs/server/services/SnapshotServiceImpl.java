package pt.eden.hbs.server.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.eden.hbs.bank.edenred.EdenredBankService;
import pt.eden.hbs.bank.edenred.EdenredSnapshot;
import pt.eden.hbs.bank.exceptions.BankException;
import pt.eden.hbs.bank.santander.SantanderBankService;
import pt.eden.hbs.bank.santander.SantanderSnapshot;
import pt.eden.hbs.bank.utils.SnapshotUtils;
import pt.eden.hbs.common.entity.Snapshot;
import pt.eden.hbs.configuration.ApplicationConfigurations;
import pt.eden.hbs.server.entity.SnapshotEntity;
import pt.eden.hbs.server.persistence.SnapshotRepository;

import java.time.LocalDateTime;

/**
 * @author : trsimoes
 */
@Service
@SuppressWarnings("unused")
public class SnapshotServiceImpl implements SnapshotService {

    private static final Logger LOG = LoggerFactory.getLogger(SnapshotServiceImpl.class);

    private final SnapshotRepository snapshotRepository;

    private final ApplicationConfigurations configurations;

    private final SantanderBankService santanderBankService;

    private final EdenredBankService edenredBankService;

    @Autowired
    public SnapshotServiceImpl(SnapshotRepository snapshotRepository, ApplicationConfigurations configurations, SantanderBankService santanderBankService, EdenredBankService edenredBankService) {
        this.snapshotRepository = snapshotRepository;
        this.configurations = configurations;
        this.santanderBankService = santanderBankService;
        this.edenredBankService = edenredBankService;
    }

    @Override
    public void takeSnapshot() {

        try {
            LOG.info("Getting Santander balance...");
            final SantanderSnapshot santanderSnapshot = this.santanderBankService.getCurrentDetails();
            SnapshotUtils.printSantanderSnapshot(santanderSnapshot);

            LOG.info("Getting Edenred balance...");
            final EdenredSnapshot edenredSnapshot = this.edenredBankService.getCurrentDetails();
            SnapshotUtils.printEdenredSnapshot(edenredSnapshot);

            LOG.info("Publishing results...");
            final Snapshot snapshot = convert(santanderSnapshot, edenredSnapshot);
            this.snapshotRepository.save(SnapshotEntity.from(snapshot));
            LOG.info("Details successfully stored in HBS database");
        } catch (BankException e) {
            LOG.error("Error getting snapshot", e);
        }
    }

    private Snapshot convert(final SantanderSnapshot santanderSnapshot, final EdenredSnapshot edenredSnapshot) {
        final Snapshot snapshot = new Snapshot();
        snapshot.setCreateDateTime(LocalDateTime.now());
        snapshot.setAccountBalance(santanderSnapshot.getAccountBalance());
        snapshot.setCreditBalance(santanderSnapshot.getCreditBalance());
        snapshot.setEuroticketBalance(edenredSnapshot.getAccountBalance());
        return snapshot;
    }
}
