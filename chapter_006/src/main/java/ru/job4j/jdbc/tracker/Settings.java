package ru.job4j.jdbc.tracker;

import java.io.*;
import java.util.Properties;

public class Settings {
    private final Properties properties = new Properties();

    public void load(InputStream inputStream) {
        try {
            this.properties.load(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void load(String config) {
        try (FileInputStream inputStream = new FileInputStream(new File(getClass().getClassLoader().getResource(config).getFile()))) {
            load(inputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getValue(String key) {
        return this.properties.getProperty(key);
    }
}
