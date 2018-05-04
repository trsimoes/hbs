package pt.eden.hbs.server.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.format.annotation.DateTimeFormat;
import pt.eden.hbs.server.entity.DailySnapshotViewEntity;

import java.time.LocalDateTime;
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

    @Query
    List<DailySnapshotViewEntity> findAllByCreateDateTimeAfterOrderByCreateDateTimeDesc(
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @Param("createDateTime") LocalDateTime createDateTime);
}
