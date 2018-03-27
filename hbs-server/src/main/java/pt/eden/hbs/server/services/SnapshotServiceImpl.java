//package pt.eden.hbs.server.services;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import pt.eden.hbs.bank.exceptions.BankException;
//import pt.eden.hbs.bank.santander.SantanderBankService;
//import pt.eden.hbs.bank.santander.SantanderSnapshot;
//import pt.eden.hbs.configuration.ApplicationConfigurations;
//import pt.eden.hbs.server.entity.SnapshotEntity;
//import pt.eden.hbs.server.persistence.SnapshotRepository;
//
///**
// * @author : trsimoes
// */
//@Service
//@SuppressWarnings("unused")
//public class SnapshotServiceImpl implements SnapshotService {
//
//    private static final Logger log = LoggerFactory.getLogger(SnapshotServiceImpl.class);
//
//    @Autowired
//    private SnapshotRepository snapshotRepository;
//
//    @Autowired
//    private ApplicationConfigurations configurations;
//
//    @Autowired
//    private SantanderBankService santanderBankService;
//
//    @Override
//    public void takeSnapshot() {
//
//        try {
//            if (log.isDebugEnabled()) {
//                log.debug("Getting details from Home Banking server");
//            }
//
//            final SantanderSnapshot snapshot = this.santanderBankService.getCurrentDetails();
//
//            if (log.isTraceEnabled()) {
//                log.trace("Details from Bank: " + snapshot.toString());
//            }
//
//            if (log.isTraceEnabled()) {
//                log.trace("Saving snapshot to database: " + snapshot.toString());
//            }
//
//            this.snapshotRepository.save(SnapshotEntity.from(snapshot));
//        } catch (BankException e) {
//            log.error("Error getting snapshot", e);
//        }
//    }
//}
