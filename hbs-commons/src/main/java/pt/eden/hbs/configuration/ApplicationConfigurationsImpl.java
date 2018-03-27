package pt.eden.hbs.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author : trsimoes
 */
@Service
@SuppressWarnings("unused")
public class ApplicationConfigurationsImpl implements ApplicationConfigurations {

    private static final Logger LOG = LoggerFactory.getLogger(ApplicationConfigurationsImpl.class);

    private Properties properties;

    @PostConstruct
    public void initialize() {
        try {
            final InputStream is = ApplicationConfigurationsImpl.class.getResourceAsStream("/hbs.properties");
            this.properties = new Properties();
            this.properties.load(is);
        } catch (Throwable e) {
            LOG.warn("Configuration file 'hbs.properties' not found.", e.getLocalizedMessage());
        }
    }

    public String get(final String key) {
        return this.properties.getProperty(key);
    }
}
