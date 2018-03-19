package pt.eden.hbs.persistence;

import org.springframework.data.repository.CrudRepository;
import pt.eden.hbs.entity.Snapshot;

/**
 * @author : trsimoes
 */
public interface SnapshotRepository extends CrudRepository<Snapshot, Long> {

    /*
    SELECT
        account_balance,
        credit_balance,
        create_date_time
    FROM (
          SELECT day(create_date_time), MAX(create_date_time) as MaxTime
          FROM SNAPSHOT
          GROUP BY day(create_date_time)
    ) r
    INNER JOIN SNAPSHOT t
    ON t.create_date_time = r.MaxTime;
     */
}
