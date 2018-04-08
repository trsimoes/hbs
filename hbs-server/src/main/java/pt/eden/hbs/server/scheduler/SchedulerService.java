package pt.eden.hbs.server.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pt.eden.hbs.server.services.SnapshotService;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class SchedulerService {

    private static final Logger log = LoggerFactory.getLogger(SchedulerService.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Autowired
    private SnapshotService snapshotService;

    /**
     * At minute 0 past hour 12 and 23.
     */
    @Scheduled(cron = "0 0 * * * *  ")
    public void reportCurrentTime() {
        if (log.isTraceEnabled()) {
            log.trace("Taking a snapshot at {}", dateFormat.format(new Date()));
        }
        this.snapshotService.takeSnapshot();
    }
}
