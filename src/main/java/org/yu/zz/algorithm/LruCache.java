package org.yu.zz.algorithm;

import java.util.LinkedHashMap;
import java.util.Map;

public class LruCache<K, V> {
    private int mMaxSize;
    private int mSize;
    private LinkedHashMap<K, V> mMap = new LinkedHashMap<>(0, .75f, true);

    public LruCache(int maxSize) {
        this.mMaxSize = maxSize;
    }

    public void put(K key, V value) {
        synchronized (this) {
            mSize += sizeOf(key, value);
            V v = mMap.put(key, value);
            if (v != null) {
                mSize -= sizeOf(key, value);
            }
            trimSize(mMaxSize);
        }
    }

    public V get(K key) {
        return mMap.get(key);
    }

    public void remove(K key) {
        synchronized (this) {
            V value = mMap.remove(key);
            if (value != null) {
                mSize -= sizeOf(key, value);
            }
        }
    }

    protected int sizeOf(K key, V value) {
        return 1;
    }

    private void trimSize(int maxSize) {
        while (true) {
            synchronized (this) {
                if (mSize <= maxSize) {
                    break;
                }
                Map.Entry<K, V> toEvict = null;
                for (Map.Entry<K, V> entrySet : mMap.entrySet()) {
                    toEvict = entrySet;
                }
                if (toEvict == null) {
                    break;
                }
                mSize--;
                K key = toEvict.getKey();
                mMap.remove(key);
            }
        }
    }
}
