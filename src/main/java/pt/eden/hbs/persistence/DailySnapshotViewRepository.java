package pt.eden.hbs.persistence;

import org.springframework.data.repository.CrudRepository;
import pt.eden.hbs.entity.DailySnapshotView;

/**
 * @author : trsimoes
 */
public interface DailySnapshotViewRepository extends CrudRepository<DailySnapshotView, Long> {
}
