package ru.job4j.collection;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class SimpleLinkedList<E> implements SimpleLinked<E> {

    private int size;
    private int modCount;
    private Node<E> head;

    @Override
    public void add(E value) {
        Node<E> curr = this.head;
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

    @Override
    public E get(int index) {
        Objects.checkIndex(index, size);
        Node<E> curr = head;
        int i = 0;
        while (index != i) {
            curr = curr.next;
            i++;
        }
        return curr.item;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private Node<E> node = head;
            final int expModCount = modCount;

            @Override
            public boolean hasNext() {
                if (expModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                return node != null;
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                E value = node.item;
                node = node.next;
                return value;
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