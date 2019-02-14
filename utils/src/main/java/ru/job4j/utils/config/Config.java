package ru.job4j.utils.config;

import java.io.*;
import java.util.*;

public class Config {
    private final Properties properties = new Properties();

    public Config(String filename) {
        this.load(filename);
    }

    private void load(String filename) {
        try (FileInputStream fis = new FileInputStream(
                new File(Objects.requireNonNull(getClass().getClassLoader().getResource(filename)).getFile()))) {
            this.properties.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String get(String key) {
        return this.properties.getProperty(key);
    }
}
