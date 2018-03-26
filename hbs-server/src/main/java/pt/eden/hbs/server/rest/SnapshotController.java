package pt.eden.hbs.server.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pt.eden.hbs.bank.HomeBankingSnapshot;
import pt.eden.hbs.server.entity.SnapshotEntity;
import pt.eden.hbs.server.persistence.SnapshotRepository;

@RestController
class SnapshotController {

    private static final Logger log = LoggerFactory.getLogger(SnapshotController.class);

    @Autowired
    private SnapshotRepository snapshotRepository;

    @RequestMapping("/get/snapshot")
    public String getSnapshots() {

        final Iterable<SnapshotEntity> all = this.snapshotRepository.findAll();
        StringBuilder result = new StringBuilder();
        for (SnapshotEntity snapshot : all) {
            result.append(snapshot.toString());
            result.append("<br/>");
        }

        return result.toString().length() == 0 ? "n/a" : result.toString();
    }

    @RequestMapping(value = "/send/snapshot", method = RequestMethod.POST)
    public ResponseEntity<HomeBankingSnapshot> update(@RequestBody final HomeBankingSnapshot snapshot) {

        this.snapshotRepository.save(SnapshotEntity.from(snapshot));

        return new ResponseEntity<>(snapshot, HttpStatus.OK);
    }
}