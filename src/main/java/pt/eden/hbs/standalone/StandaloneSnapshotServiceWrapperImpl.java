package pt.eden.hbs.standalone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import pt.eden.hbs.services.SnapshotService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author : trsimoes
 */
@Configuration
@EnableTransactionManagement
@Service
@Transactional
public class StandaloneSnapshotServiceWrapperImpl implements StandaloneSnapshotServiceWrapper {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private SnapshotService snapshotService;

    @Override
    public void takeSnapshot() {
        try {
            this.snapshotService.takeSnapshot();
        } finally {
            System.out.println("Hello World!");
        }
    }
}
