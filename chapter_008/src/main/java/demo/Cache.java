package demo;

public abstract class Cache<K, V> {
    abstract V get(K key);
    abstract int size();
}
