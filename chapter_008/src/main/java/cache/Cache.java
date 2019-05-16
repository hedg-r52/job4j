package cache;

/**
 * Cache interface
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public interface Cache<K, V> {
    /**
     * Get string from cache
     * @param key key for get cache
     * @return string value
     */
    K get(V key);

    /**
     * Get size of cache
     * @return integer value of cache size
     */
    int size();
}
