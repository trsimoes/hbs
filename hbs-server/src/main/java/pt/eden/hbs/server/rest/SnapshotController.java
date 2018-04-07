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
import pt.eden.hbs.common.entity.Snapshot;
import pt.eden.hbs.server.entity.SnapshotEntity;
import pt.eden.hbs.server.persistence.SnapshotRepository;

@RestController
class SnapshotController {

    @Autowired
    private SnapshotRepository snapshotRepository;

    @RequestMapping(value = "/send/snapshot", method = RequestMethod.POST)
    public ResponseEntity<Snapshot> update(@RequestBody final Snapshot snapshot) {

        this.snapshotRepository.save(SnapshotEntity.from(snapshot));

        return new ResponseEntity<>(snapshot, HttpStatus.OK);
    }
}