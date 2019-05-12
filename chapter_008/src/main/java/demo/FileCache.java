package demo;

import java.io.IOException;
import java.lang.ref.SoftReference;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

public class FileCache extends Cache<String, String> {
    HashMap<String, SoftReference<String>> storage = new HashMap<>();

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    public String get(String key) {
        return (storage.containsKey(key) && storage.get(key).get() != null)
                ? storage.get(key).get()
                : getFromFile(storage, key);
    }

    private String getFromFile(HashMap<String, SoftReference<String>> storage, String key) {
        String result = "";
        try {
            byte[] encoded = Files.readAllBytes(Paths.get(key));
            result = new String(encoded, Charset.defaultCharset());
        } catch (IOException e) {
            e.printStackTrace();
        }
        storage.put(key, new SoftReference<>(result));
        return result;
    }
}