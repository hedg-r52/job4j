package cache;

import java.io.IOException;
import java.lang.ref.SoftReference;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

public class FileCache extends BaseCache {
    HashMap<String, SoftReference<String>> storage = new HashMap<>();


    @Override
    protected String load(String key) throws IOException {
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