package org.learning.core;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Two method for implementing sorting on custom object
 *  1: Comparable interface implemented by custom object : compareTo(Other obj)
 *  2: Providing Comparator implementation to sort method : compare(obj1, obj2)
 *
 *  Methods that accept comparator:
 *
 *  Collections.sort(collection, comparator)
 *  Collections.binarySearch(Collection, key, comparator)
 *  Collections.max(collection, comparator)
 *  Collections.min(collection, comparator)
 *
 *  Collection.stream().sorted(comparator).toList()
 *  Collection.sort(comparator)
 *
 *  new TreeSet(comparator)
 *  new TreeMap(comparator)
 */
class SortingDataTest {

    private record Person(String name, int age){}

    @DisplayName("naturalOrder reverseOrder")
    @Test
    void test(){
        var l = Stream.of(54, 78, 23, 1, 45)
                .sorted(Comparator.naturalOrder())
                .toList();
        assertThat(l).containsExactly(1, 23, 45, 54, 78);

        l = Stream.of(54, 78, 23, 1, 45)
                .sorted(Comparator.reverseOrder())
                .toList();
        assertThat(l).containsExactly(78, 54, 45, 23, 1);
    }

    /**
     * Static helper methods : comparing, comparingInt, comparingDouble, comparingLong, naturalOrder, reverseOrder
     * Method for chaining : thenComparing, thenComparingDouble, thenComparingInt, thenComparingLong, reversed
     */
    @DisplayName(", reversed, thenComparing")
    @Test
    void test1(){
        var l = Stream.of(new Person("Sanjay", 35),
                        new Person("Mahesh", 26),
                        new Person("Akshay", 26),
                        new Person("Kishor", 26),
                        new Person("Zeel", 5))
                .sorted(
                        Comparator.comparingInt(Person::age)
                        .reversed()
                        .thenComparing(Person::name)
                )
                .toList();

        assertThat(l).containsExactly(
                new Person("Sanjay", 35),
                new Person("Akshay", 26),
                new Person("Kishor", 26),
                new Person("Mahesh", 26),
                new Person("Zeel", 5)

        );

    }

}
