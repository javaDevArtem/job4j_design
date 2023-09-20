package ru.job4j.iterator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

class ListUtilsTest {

    private List<Integer> input;

    @BeforeEach
    void setUp() {
        input = new ArrayList<>(Arrays.asList(1, 3));
    }

    @Test
    void whenAddBefore() {
        ListUtils.addBefore(input, 1, 2);
        assertThat(input).hasSize(3).containsSequence(1, 2, 3);
    }

    @Test
    void whenAddBeforeWithInvalidIndex() {
        assertThatThrownBy(() -> ListUtils.addBefore(input, 3, 2))
                .isInstanceOf(IndexOutOfBoundsException.class);
    }

    @Test
    void whenAddAfter() {
        ListUtils.addAfter(input, 0, 2);
        assertThat(input).hasSize(3).containsSequence(1, 2, 3);
    }

    @Test
    void whenRemoveByPredicate() {
        ListUtils.addAfter(input, 0, 7);
        ListUtils.removeIf(input, integer -> integer > 5);
        assertThat(input).hasSize(2).containsSequence(1, 3);
    }

    @Test
    void whenReplaceByPredicate() {
        ListUtils.addAfter(input, 0, 7);
        ListUtils.replaceIf(input, integer -> integer < 3, 10);
        assertThat(input).hasSize(3).containsSequence(10, 7, 3);
    }

    @Test
    void whenRemoveAllIfContains() {
        List<Integer> integers = new ArrayList<>();
        integers.add(1);
        integers.add(2);
        integers.add(3);
        List<Integer> duplicates = Arrays.asList(2, 3);
        ListUtils.removeAll(integers, duplicates);
        assertThat(integers).hasSize(1).containsSequence(1);
    }
}