package ru.otus;

import java.util.List;

public interface HwCache<K, V> {

    void put(K key, V value);

    void remove(K key);

    V get(K key);

    int getSizeCache();

    void addListener(HwListener<K, V> listener);

    void removeListener(HwListener<K, V> listener);

    List<V> getAll();
}
