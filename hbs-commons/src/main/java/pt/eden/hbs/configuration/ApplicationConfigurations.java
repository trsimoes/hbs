package pt.eden.hbs.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author : trsimoes
 */
public class ApplicationConfigurations {

    private Properties properties;

    private static ApplicationConfigurations INSTANCE = new ApplicationConfigurations();

    public static ApplicationConfigurations getInstance() {
        return INSTANCE;
    }

    private ApplicationConfigurations() {
        try {
            final InputStream is = ApplicationConfigurations.class.getResourceAsStream("/hbs.properties");
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
