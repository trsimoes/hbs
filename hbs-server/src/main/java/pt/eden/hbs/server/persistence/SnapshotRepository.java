package pt.eden.hbs.server.persistence;

import org.springframework.data.repository.CrudRepository;
import pt.eden.hbs.server.entity.Snapshot;

/**
 * @author : trsimoes
 */
public interface SnapshotRepository extends CrudRepository<Snapshot, Long> {
}
