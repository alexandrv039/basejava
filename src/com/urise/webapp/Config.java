package com.urise.webapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Config {
    private static final File PROPERTIES_DIR = new File("./config/resumes.properties");
    private static final Config INSTANCE = new Config();
    private static Properties props = new Properties();
    private Map<String, String> storage;

    public static Config get() {
        return INSTANCE;
    }

    private Config() {
        try (InputStream is = new FileInputStream(PROPERTIES_DIR)) {
            props = new Properties();
            props.load(is);
            storage = new HashMap<>();
            storage.put("storageDir", props.getProperty("storage.dir"));
            storage.put("dbUrl", props.getProperty("db.url"));
            storage.put("dbUser", props.getProperty("db.user"));
            storage.put("dbPassword", props.getProperty("db.password"));
        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file " + PROPERTIES_DIR.getAbsolutePath());
        }
    }

    public String getStorageDir() {
        return storage.get("storageDir");
    }

    public String getDbUrl() {
        return storage.get("dbUrl");
    }

    public String getDbUser() {
        return storage.get("dbUser");
    }

    public String getDbPassword() {
        return storage.get("dbPassword");
    }
}
