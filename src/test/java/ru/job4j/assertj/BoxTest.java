package ru.job4j.assertj;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.withPrecision;

class BoxTest {
    @Test
    void isThisSphere() {
        Box box = new Box(0, 10);
        String name = box.whatsThis();
        assertThat(name).isEqualTo("Sphere");
    }

    @Test
    void isThisTetrahedron() {
        Box box = new Box(4, 4);
        String name = box.whatsThis();
        assertThat(name).isEqualTo("Tetrahedron")
                .containsIgnoringCase("tetrahedron")
                .endsWith("dron");
    }

    @Test
    void checkNumberOfVerticesForTetrahedron() {
        Box box = new Box(4, 4);
        int number = box.getNumberOfVertices();
        assertThat(number).isEqualTo(4)
                .isPositive()
                .isNotZero()
                .isEven();
    }

    @Test
    void checkNumberOfVerticesForSphere() {
        Box box = new Box(0, 10);
        int number = box.getNumberOfVertices();
        assertThat(number).isEqualTo(0)
                .isLessThan(1);
    }

    @Test
    void checkThatExist() {
        Box box = new Box(8, 4);
        boolean exist = box.isExist();
        assertThat(exist).isTrue();
    }

    @Test
    void checkThatNotExist() {
        Box box = new Box(1, 0);
        boolean exist = box.isExist();
        assertThat(exist).isFalse();
    }

    @Test
    void checkBoxAreaForCube() {
        Box box = new Box(8, 4);
        double area = box.getArea();
        assertThat(area).isCloseTo(96d, withPrecision(0.01d))
                .isGreaterThan(50d);
    }

    @Test
    void checkBoxAreaForTetrahedron() {
        Box box = new Box(4, 4);
        double area = box.getArea();
        assertThat(area).isCloseTo(27.71d, withPrecision(0.01d))
                .isLessThan(30.26d);
    }
}