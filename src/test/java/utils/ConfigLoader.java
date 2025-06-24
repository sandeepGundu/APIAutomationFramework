package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = new FileInputStream("src/test/resources/config/secrets.properties")) {
            properties.load(input);
        } catch (IOException ex) {
            throw new RuntimeException("Failed to load secrets.properties", ex);
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }
}

