package pt.eden.hbs.server.persistence;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import pt.eden.hbs.common.entity.Snapshot;
import pt.eden.hbs.server.entity.SnapshotEntity;

/**
 * @author : trsimoes
 */
public interface SnapshotRepository extends CrudRepository<SnapshotEntity, Long> {

    @Query
    Snapshot findTop1ByOrderByCreateDateTimeDesc();
}
