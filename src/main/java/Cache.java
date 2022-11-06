package main.java;

import java.util.Optional;

public interface Cache<K, V> {
    Optional<V> get(K key);
    void put(K key, V value);
    void remove(K key);
    int size();
    boolean isEmpty();
    void clear();
}
