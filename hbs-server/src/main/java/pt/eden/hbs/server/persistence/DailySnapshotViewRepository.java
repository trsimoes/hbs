package pt.eden.hbs.server.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import pt.eden.hbs.server.entity.DailySnapshotViewEntity;

import java.util.List;

/**
 * @author : trsimoes
 */
@RepositoryRestResource(collectionResourceRel = "snapshotview", path = "snapshotview")
public interface DailySnapshotViewRepository extends JpaRepository<DailySnapshotViewEntity, Long> {

    @Query
    List<DailySnapshotViewEntity> findTop10ByOrderByCreateDateTimeDesc();

    @Query
    DailySnapshotViewEntity findTop1ByOrderByCreateDateTimeDesc();
}
