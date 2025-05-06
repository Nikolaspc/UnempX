package com.myorg.unempx.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Provides application-wide configuration, including data storage paths.
 */
public class Config {
    private static final String APP_DIR = System.getProperty("user.home") + "/.unempx";
    private static final String DATA_FILE = "unemployment_data.csv";

    /**
     * Ensures the application data directory exists and returns its path.
     * @return Path to the application data directory.
     * @throws IOException if the directory cannot be created.
     */
    public static Path getDataDirectory() throws IOException {
        Path dir = Paths.get(APP_DIR);
        if (Files.notExists(dir)) {
            Files.createDirectories(dir);
        }
        return dir;
    }

    /**
     * Returns the full path to the unemployment data CSV file in the external directory.
     * @return Path to the CSV data file.
     * @throws IOException if the data directory cannot be initialized.
     */
    public static Path getDataFilePath() throws IOException {
        return getDataDirectory().resolve(DATA_FILE);
    }
}
