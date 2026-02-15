package ru.otus;

import java.util.*;

public class MyCache<K, V> implements HwCache<K, V> {
    private final Map<K, V> cache = new WeakHashMap<>();
    private final List<HwListener<K, V> > listeners = new ArrayList<>();

    @Override
    public void put(K key, V value) {
        cache.put(key, value);
        callListeners(key, value, "put");
    }

    @Override
    public void remove(K key) {
        V value = cache.remove(key);
        callListeners(key, value, "remove");
    }

    @Override
    public V get(K key) {
        V value = cache.get(key);
        callListeners(key, value, "get");
        return value;
    }

    @Override
    public List<V> getAll() {
        List<V> allValues = new ArrayList<>();

        for (K key : cache.keySet()) {
            allValues.add(get(key));
        }

        return allValues;
    }

    @Override
    public int getSizeCache(){
        return cache.size();
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        if (listener != null)
            listeners.add(listener);
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        listeners.remove(listener);
    }

    private void callListeners(K key, V value, String action) {
        listeners.forEach(listener -> listener.notify(key, value, action));
    }
}
