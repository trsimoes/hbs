package pt.eden.hbs.server.persistence;

import org.springframework.data.repository.CrudRepository;
import pt.eden.hbs.server.entity.DailySnapshotView;

/**
 * @author : trsimoes
 */
public interface DailySnapshotViewRepository extends CrudRepository<DailySnapshotView, Long> {
}
