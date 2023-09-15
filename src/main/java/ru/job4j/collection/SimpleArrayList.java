package ru.job4j.collection;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class SimpleArrayList<T> implements SimpleList<T> {

    private T[] container;
    private int size;
    private int modCount;

    public SimpleArrayList(int capacity) {
        container = (T[]) new Object[capacity];
    }

    @Override
    public void add(T value) {
        if (size >= container.length) {
            makeNewCapacity();
        }
        container[size++] = value;
        modCount++;
    }

    private void makeNewCapacity() {
        int newCapacity = container.length == 0 ? 1 : container.length * 2;
        T[] newArr = (T[]) new Object[newCapacity];
        System.arraycopy(container, 0, newArr, 0, container.length);
        container = newArr;
    }


    @Override
    public T set(int index, T newValue) {
        if (index >= size) {
            throw new IndexOutOfBoundsException();
        }
        T rem = container[index];
        container[index] = newValue;
        return rem;
    }

    @Override
    public T remove(int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException();
        }
        T rem = container[index];
        container[index] = null;
        System.arraycopy(container, index + 1, container, index, size - index - 1);
        size--;
        modCount--;
        return rem;
    }

    @Override
    public T get(int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException();
        }
        return container[index];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            int cursor = 0;
            final int expectedModCount = modCount;

            @Override
            public boolean hasNext() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                return cursor < size;
            }

            @Override
            public T next() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return container[cursor++];
            }
        };
    }
}