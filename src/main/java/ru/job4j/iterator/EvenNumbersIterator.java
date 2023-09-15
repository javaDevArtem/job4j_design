package ru.job4j.iterator;

import java.util.*;

public class EvenNumbersIterator implements Iterator<Integer> {

    private int[] data;
    private int index;

    public EvenNumbersIterator(int[] data) {
        this.data = data;
    }

    @Override
    public boolean hasNext() {
        boolean result = false;
        for (int i = index; i < data.length; i++) {
            if (data[i] % 2 == 0) {
                result = true;
                break;
            }
        }
        return result;
    }

    @Override
    public Integer next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return checkIfEven(data[index++]) ? data[index - 1] : this.next();
    }

    private boolean checkIfEven(Integer num) {
        return num % 2 == 0;
    }
}