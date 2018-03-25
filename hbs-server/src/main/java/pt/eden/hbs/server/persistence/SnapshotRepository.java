package pt.eden.hbs.server.persistence;

import org.springframework.data.repository.CrudRepository;
import pt.eden.hbs.server.entity.SnapshotEntity;

/**
 * @author : trsimoes
 */
public interface SnapshotRepository extends CrudRepository<SnapshotEntity, Long> {
}
