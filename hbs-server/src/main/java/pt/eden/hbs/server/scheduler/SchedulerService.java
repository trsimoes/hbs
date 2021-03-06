package pt.eden.hbs.server.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pt.eden.hbs.configuration.ApplicationConfigurations;
import pt.eden.hbs.server.services.SnapshotService;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class SchedulerService {

    private static final Logger log = LoggerFactory.getLogger(SchedulerService.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    private final SnapshotService snapshotService;

    private final ApplicationConfigurations configurations;

    @Autowired
    public SchedulerService(SnapshotService snapshotService, ApplicationConfigurations configurations) {
        this.snapshotService = snapshotService;
        this.configurations = configurations;
    }

    /**
     * At minute 0 past hour 12 and 23.
     */
    @Scheduled(cron = "0 0 * * * *  ")
    public void reportCurrentTime() {

        if (this.configurations != null) {

            final String schedulerEnabledString = this.configurations.get("scheduler.enabled");

            if (log.isTraceEnabled()) {
                log.trace("Configuration 'scheduler.enabled' loaded as string with value: " + schedulerEnabledString);
            }

            final boolean schedulerEnabled = Boolean.valueOf(schedulerEnabledString);

            if (log.isDebugEnabled()) {
                log.debug("Configuration 'scheduler.enabled' loaded with value: " + schedulerEnabled);
            }

            if (!schedulerEnabled) {
                if (log.isDebugEnabled()) {
                    log.debug("Scheduler is disabled, snapshot will not be taken");
                }
                return;
            }
        }

        if (log.isTraceEnabled()) {
            log.trace("Taking a snapshot at {}", dateFormat.format(new Date()));
        }
        this.snapshotService.takeSnapshot();
    }
}
