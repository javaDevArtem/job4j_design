package ru.job4j.map;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class NonCollisionMap<K, V> implements SimpleMap<K, V> {

    private static final float LOAD_FACTOR = 0.75f;

    private int capacity = 8;

    private int count = 0;

    private int modCount = 0;

    private MapEntry<K, V>[] table = new MapEntry[capacity];

    @Override
    public boolean put(K key, V value) {
        boolean result = false;
        if (count >= capacity * LOAD_FACTOR) {
            expand();
        }
        int h = hash(Objects.hashCode(key));
        int index = indexFor(h);
        if (table[index] == null) {
            table[index] = new MapEntry<>(key, value);
            count++;
            modCount++;
            result = true;
        }
        return result;
    }

    private int hash(int hashCode) {
        return (hashCode == 0) ? 0 : (hashCode) ^ (hashCode >>> 16);
    }

    private int indexFor(int hash) {
        return hash & (table.length - 1);
    }

    private void expand() {
        MapEntry<K, V>[] newTable = new MapEntry[capacity * 2];
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null) {
                int h = hash(Objects.hashCode(table[i].key));
                int index = indexFor(h);
                newTable[index] = table[i];
            }
        }
        table = newTable;
    }

    @Override
    public V get(K key) {
        V result = null;
        int index = getIndex(key);
        MapEntry<K, V> mapEntry = table[index];
        if (mapEntry != null) {
            K key1 = mapEntry.key;
            if (checkEquality(key, key1)) {
                result = mapEntry.value;
            }
        }
        return result;
    }

    @Override
    public boolean remove(K key) {
        boolean result = false;
        int index = getIndex(key);
        MapEntry<K, V> mapEntry = table[index];
        if (mapEntry != null) {
            K key1 = mapEntry.key;
            if (checkEquality(key, key1)) {
                table[index] = null;
                count--;
                modCount--;
                result = true;
            }
        }
        return result;
    }

    @Override
    public Iterator<K> iterator() {
        return new Iterator<K>() {
            final int expModCount = modCount;
            private int index;

            @Override
            public boolean hasNext() {
                if (expModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                while (index < table.length && table[index] == null) {
                    index++;
                }
                return index < table.length;
            }

            @Override
            public K next() {
                if (expModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return table[index++].key;
            }
        };
    }

    private boolean checkEquality(K key, K key1) {
        return Objects.hashCode(key) == Objects.hashCode(key1) && Objects.equals(key, key1);
    }

    private int getIndex(K key) {
        int h = hash(Objects.hashCode(key));
        return indexFor(h);
    }

    private static class MapEntry<K, V> {

        K key;
        V value;

        public MapEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}