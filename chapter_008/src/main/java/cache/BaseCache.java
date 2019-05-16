package cache;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

/**
 * Base cache
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public abstract class BaseCache implements Cache<String, String> {
    private final Map<String, SoftReference<String>> storage = new HashMap<>();

    @Override
    public String get(String key) {
        String value = null;
        if (storage.containsKey(key)) {
            value = storage.get(key).get();
        }
        if (value == null) {
            try {
                value = load(key);
                storage.put(key, new SoftReference(value));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return value;
    }

    /**
     * Get size of cache
     * @return size of cache
     */
    @Override
    public int size() {
        return storage.size();
    }

    /**
     * loading pair key-values
     * @param key
     * @return
     * @throws Exception
     */
    abstract protected String load(String key) throws Exception;
}
