package pt.eden.hbs.server.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.CollectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pt.eden.hbs.common.entity.Snapshot;
import pt.eden.hbs.common.entity.SnapshotExt;
import pt.eden.hbs.server.entity.DailySnapshotViewEntity;
import pt.eden.hbs.server.entity.SnapshotEntity;
import pt.eden.hbs.server.persistence.DailySnapshotViewRepository;
import pt.eden.hbs.server.persistence.SnapshotRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@RestController
public class SnapshotController {

    @Autowired
    private SnapshotRepository snapshotRepository;

//    @Autowired
//    private DailySnapshotViewRepository dailySnapshotViewRepository;

    /**
     * This method is used by the updater app
     */
    @RequestMapping(value = "/send/snapshot", method = RequestMethod.POST)
    public ResponseEntity<Snapshot> update(@RequestBody final Snapshot snapshot) {

        this.snapshotRepository.save(SnapshotEntity.from(snapshot));

        return new ResponseEntity<>(snapshot, HttpStatus.OK);
    }

//    @RequestMapping("/find/snapshot/last10days")
//    public ResponseEntity<List<SnapshotExt>> getLast10Days() {
//        final List<DailySnapshotViewEntity> list = this.dailySnapshotViewRepository
//                .findTop10ByOrderByCreateDateTimeDesc();
//        List<SnapshotExt> result = CollectionUtils.transform(list, o -> {
//            DailySnapshotViewEntity dsve = (DailySnapshotViewEntity) o;
//            return dsve.convertToSnapshotExt();
//        });
//        return new ResponseEntity<>(result, HttpStatus.OK);
//    }
//
//    @RequestMapping("/find/snapshot/alldays")
//    public ResponseEntity<List<SnapshotExt>> getAllDays() {
//        final Iterable<DailySnapshotViewEntity> collection = this.dailySnapshotViewRepository.findAll();
//        final Iterator<DailySnapshotViewEntity> iteratorToList = collection.iterator();
//        final List<SnapshotExt> list = new ArrayList<>();
//        while (iteratorToList.hasNext()) {
//            list.add(iteratorToList.next());
//        }
//        Collections.reverse(list);
//        List<SnapshotExt> result = CollectionUtils.transform(list, o -> {
//            DailySnapshotViewEntity dsve = (DailySnapshotViewEntity) o;
//            return dsve.convertToSnapshotExt();
//        });
//        return new ResponseEntity<>(result, HttpStatus.OK);
//    }
//
//    @RequestMapping("/find/snapshot/latest")
//    public ResponseEntity<SnapshotExt> getLatestSnapshot() {
//        final DailySnapshotViewEntity snapshot = this.dailySnapshotViewRepository.findTop1ByOrderByCreateDateTimeDesc();
//        return new ResponseEntity<>(snapshot.convertToSnapshotExt(), HttpStatus.OK);
//    }
}