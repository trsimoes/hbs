package pt.eden.hbs.configuration;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author : trsimoes
 */
@Service
public class ApplicationConfigurationsImpl implements ApplicationConfigurations {

    private Properties properties;

    @PostConstruct
    public void initialize() {
        try {
            final InputStream is = ApplicationConfigurationsImpl.class.getResourceAsStream("/hbs.properties");
            this.properties = new Properties();
            this.properties.load(is);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error loading 'hbs.properties' configuration file.", e);
        }
    }

    public String get(final String key) {
        return this.properties.getProperty(key);
    }
}
