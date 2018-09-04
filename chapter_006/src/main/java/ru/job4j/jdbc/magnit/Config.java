package ru.job4j.jdbc.magnit;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Config {
    private final Properties properties = new Properties();

    public void load(String filename) {
        try (FileInputStream inputStream =
                     new FileInputStream(new File(getClass().getClassLoader().getResource(filename).getFile()))) {
            this.properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getValue(String key) {
        return this.properties.getProperty(key);
    }
}
