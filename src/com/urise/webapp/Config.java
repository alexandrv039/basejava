package com.urise.webapp;

import com.urise.webapp.storage.SqlStorage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final File PROPERTIES_DIR = new File("./config/resumes.properties");
    private static final Config INSTANCE = new Config();
    private static Properties props = new Properties();
    private final String storageDir;
    private final SqlStorage storage;

    public static Config get() {
        return INSTANCE;
    }

    private Config() {
        try (InputStream is = new FileInputStream(PROPERTIES_DIR)) {
            props = new Properties();
            props.load(is);
            storageDir = props.getProperty("storage.dir");
            storage = new SqlStorage(props.getProperty("db.url"),
                    props.getProperty("db.user"),
                    props.getProperty("db.password"));
        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file " + PROPERTIES_DIR.getAbsolutePath());
        }
    }

    public String getStorageDir() {
        return storageDir;
    }

    public SqlStorage getStorage() {
        return storage;
    }
}
