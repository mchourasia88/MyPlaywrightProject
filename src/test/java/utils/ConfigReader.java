package utils;

import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private static final Properties properties = new Properties();

    static {
        try (InputStream input =
                     ConfigReader.class
                             .getClassLoader()
                             .getResourceAsStream("config.properties")) {

            if (input == null) {
                throw new RuntimeException("config.properties not found");
            }

            properties.load(input);

        } catch (Exception e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    /**
     * Generic getter:
     * 1. System property (runtime override)
     * 2. config.properties
     */
    public static String get(String key) {
        String systemValue = System.getProperty(key);
        return (systemValue != null && !systemValue.isEmpty())
                ? systemValue
                : properties.getProperty(key);
    }

    /**
     * Environment getter with default
     */
    public static String getEnv() {
        return System.getProperty(
                "env",
                properties.getProperty("env", "qa")
        );
    }

    /**
     * Get URL based on environment
     * Example keys:
     * qa.url, uat.url, prod.url
     */
    public static String getAppUrl() {
        String env = getEnv();
        return properties.getProperty(env + ".url");
    }
}
