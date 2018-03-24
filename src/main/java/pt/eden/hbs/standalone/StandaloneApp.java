package pt.eden.hbs.standalone;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import pt.eden.hbs.conf.ApplicationConfigurations;

import java.io.File;

/**
 * @author : trsimoes
 */
@ComponentScan(basePackages = "pt.eden.hbs")
public class StandaloneApp {

    public static void main(String[] args) {

        final ApplicationConfigurations configurations = ApplicationConfigurations.getInstance();
        final String driverPath = configurations.get("webdriver.gecko.driver");
        if (!new File(driverPath).isFile()) {
            throw new IllegalArgumentException(
                    "The 'webdriver.gecko.driverPath' property points to an invalid " + "location: " + driverPath
                            + ". Please verify the driver path.");
        }

        if (StringUtils.isNotBlank(driverPath)) {
            System.setProperty("webdriver.gecko.driver", driverPath);
        } else {
            throw new IllegalArgumentException("The 'webdriver.gecko.driverPath' property is not correctly specified "
                    + "in the 'hbs.properties' file.");
        }

        ApplicationContext ctx = null;
        //        try {
        ctx = new AnnotationConfigApplicationContext(StandaloneApp.class);
        StandaloneSnapshotServiceWrapper snapshotService = ctx.getBean(StandaloneSnapshotServiceWrapper.class);
        snapshotService.takeSnapshot();
        //        } finally {
        //            if (ctx != null) {
        //                final EntityManager entityManager = ctx.getBean(EntityManager.class);
        //                entityManager.flush();
        //            }
        //        }
    }
}
