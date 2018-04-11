package pt.eden.hbs.server.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import pt.eden.hbs.common.entity.Snapshot;
import pt.eden.hbs.server.entity.SnapshotEntity;

/**
 * @author : trsimoes
 */
@RepositoryRestResource(collectionResourceRel = "snapshot", path = "snapshot")
public interface SnapshotRepository extends JpaRepository<SnapshotEntity, Long> {

    @Query
    Snapshot findTop1ByOrderByCreateDateTimeDesc();
}
