package ru.job4j.assertj;

import org.assertj.core.data.Index;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class SimpleConvertTest {
    @Test
    void checkArray() {
        SimpleConvert simpleConvert = new SimpleConvert();
        String[] array = simpleConvert.toArray("first", "second", "three", "four", "five");
        assertThat(array).hasSize(5)
                .contains("second")
                .contains("first", Index.atIndex(0))
                .containsAnyOf("zero", "second", "six")
                .doesNotContain("first", Index.atIndex(1));
    }

    @Test
    void checkList() {
        SimpleConvert simpleConvert = new SimpleConvert();
        List<String> list = simpleConvert.toList("1", "2", "3", "4", "5");
        assertThat(list).hasSize(5)
                .isNotNull()
                .first().isEqualTo("1");
        assertThat(list).element(3).isNotNull();
        assertThat(list).last().isNotNull()
                .isEqualTo("5");
    }

    @Test
    void checkSet() {
        SimpleConvert simpleConvert = new SimpleConvert();
        Set<String> set = simpleConvert.toSet("7", "6", "5", "4", "Three");
        assertThat(set).hasSize(5)
                .contains("Three")
                .containsAnyOf("7")
                .noneMatch(e -> e.equals("Four"))
                .last().isNotNull();
    }

    @Test
    void toMap() {
        SimpleConvert simpleConvert = new SimpleConvert();
        Map<String, Integer> map = simpleConvert.toMap("7", "6");
        assertThat(map).hasSize(2)
                .containsKey("7")
                .containsValue(1)
                .doesNotContainKey("0")
                .containsEntry("7", 0);
    }
}