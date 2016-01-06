package org.terra.planet.status;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesFileReader {

    private static final Properties properties;

    /** Use a static initializer to read from file. */
    static {
        InputStream inputStream = PropertiesFileReader.class.getResourceAsStream("/app.properties");
        properties = new Properties();
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read properties file", e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    // Ignore
                }
            }
        }
    }

    /** Hide default constructor. */
    private PropertiesFileReader() {
    }

    /**
     * Gets the Git SHA-1.
     * 
     * @return A {@code String} with the Git SHA-1.
     */
    public static String getGitSha1() {
        return properties.getProperty("git-sha-1");
    }
}
