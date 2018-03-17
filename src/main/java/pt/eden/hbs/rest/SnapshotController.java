package pt.eden.hbs.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.eden.hbs.entity.Snapshot;
import pt.eden.hbs.persistence.SnapshotRepository;

@RestController
class SnapshotController {

    private static final Logger log = LoggerFactory.getLogger(SnapshotController.class);

    @Autowired
    private SnapshotRepository snapshotRepository;

    @RequestMapping("/snapshot")
    String execute() {
        final Iterable<Snapshot> all = this.snapshotRepository.findAll();
        StringBuilder result = new StringBuilder();
        for (Snapshot snapshot : all) {
            result.append(snapshot.toString());
            result.append("<br/>");
        }

        return result.toString().length() == 0 ? "n/a" : result.toString();
    }
}