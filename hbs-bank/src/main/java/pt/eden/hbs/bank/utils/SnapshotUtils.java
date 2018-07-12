package pt.eden.hbs.bank.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.eden.hbs.bank.edenred.EdenredSnapshot;
import pt.eden.hbs.bank.santander.SantanderSnapshot;

public class SnapshotUtils {

    private static final Logger LOG = LoggerFactory.getLogger(SnapshotUtils.class);

    public static void printSantanderSnapshot(final SantanderSnapshot snapshot) {

        if (snapshot != null) {
            LOG.info("Current Balance at " + snapshot.getCreateDateTime() + " is:");
            LOG.info("\tAccount Balance:\t" + snapshot.getAccountBalance());
            LOG.info("\tCredit Balance:\t\t" + snapshot.getCreditBalance());
        } else {
            LOG.warn("Could not fetch current balance.");
        }
    }

    public static void printEdenredSnapshot(final EdenredSnapshot snapshot) {

        if (snapshot != null) {
            LOG.info("Current Balance at " + snapshot.getCreateDateTime() + " is:");
            LOG.info("\tAccount Balance:\t" + snapshot.getAccountBalance());
        }
    }
}
