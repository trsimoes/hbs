package pt.eden.hbs.persistence;

import org.springframework.data.repository.CrudRepository;
import pt.eden.hbs.entity.Snapshot;

/**
 * @author : trsimoes
 */
public interface SnapshotRepository extends CrudRepository<Snapshot, Long> {
}
