package ru.job4j.iterator;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EvenNumbersIterator implements Iterator<Integer> {

    private int[] data;
    private int index;

    public EvenNumbersIterator(int[] data) {
        this.data = data;
    }

    @Override
    public boolean hasNext() {
        return Arrays.stream(data)
                .boxed()
                .collect(Collectors.toList()).stream()
                .skip(index)
                .anyMatch(this::checkIfEven);
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