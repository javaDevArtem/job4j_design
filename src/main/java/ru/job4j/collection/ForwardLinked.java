package ru.job4j.collection;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class ForwardLinked<T> implements Iterable<T> {
    private int size = 0;
    private int modCount = 0;
    private Node<T> head;

    public void add(T value) {
        Node<T> curr = this.head;
        if (this.head == null) {
            this.head = new Node<>(value, null);
            size++;
            modCount++;
            return;
        }
        while (curr.next != null) {
            curr = curr.next;
        }
        curr.next = new Node<>(value, null);
        size++;
        modCount++;
    }

    public void addFirst(T value) {
        Node<T> newNode = new Node<>(value, null);
        if (head == null) {
            head = newNode;
            size++;
            modCount++;
            return;
        }
        Node<T> curr = head;
        head = newNode;
        head.next = curr;
        size++;
        modCount++;
    }

    public T get(int index) {
        Objects.checkIndex(index, size);
        Node<T> curr = head;
        int i = 0;
        while (index != i) {
            curr = curr.next;
            i++;
        }
        return curr.item;
    }

    public T deleteFirst() {
        if (head == null) {
            throw new NoSuchElementException();
        }
        Node<T> curr = head;
        Node<T> next = null;
        if (curr.next != null) {
            next = curr.next;
        }
        T deletedItem = curr.item;
        curr.item = null;
        curr.next = null;
        head = next;
        size--;
        modCount--;
        return deletedItem;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            int cursor = 0;
            final int expModCount = modCount;

            @Override
            public boolean hasNext() {
                if (expModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                return cursor < size;
            }

            @Override
            public T next() {
                if (expModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                Node<T> curr = head;
                for (int i = 0; i < cursor; i++) {
                    curr = curr.next;
                }
                cursor++;
                return curr.item;
            }
        };
    }

    private static class Node<E> {
        private E item;
        private Node<E> next;

        Node(E element, Node<E> next) {
            this.item = element;
            this.next = next;
        }
    }
}