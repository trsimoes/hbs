package pt.eden.hbs.server.persistence;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import pt.eden.hbs.server.entity.DailySnapshotViewEntity;

/**
 * @author : trsimoes
 */
public interface DailySnapshotViewRepository extends CrudRepository<DailySnapshotViewEntity, Long> {

    @Query
    DailySnapshotViewEntity findTop1ByOrderByCreateDateTimeDesc();
}
