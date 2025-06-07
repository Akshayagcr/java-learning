package org.learning;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Source : Stream.of(), Stream.empty(), Stream.generate(()->{}), Stream.iterate(), Collections.stream(), Collections.parallelStream()
 *
 * Intermediate operators : filter(), map(), flatMap(), sorted(), distinct(), limit(), skip(), peek()
 *
 * Terminal operators : findFirst(), findAny(), count(), collect(), reduce(), anyMatch(), noneMatch(), allMatch(), foreach(), min(), max()
 *
 * IntStream, FloatStream, DoubleStream : ****** summaryStatistics()
 *
 */
class StreamTest {


    @DisplayName("Find the sum of all even numbers in a list of integers.")
    @Test
    void test(){
        var s = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10).stream()
                .filter(ele -> ele % 2 == 0)
                .reduce(0, (sum, ele) -> sum + ele);
        assertThat(s).isEqualTo(30);
    }

    @DisplayName("Find and print the count of strings that have length greater than 5")
    @Test
    void test1(){
        var c = List.of("Akshay", "Max", "Sumeet").stream()
                .filter(ele -> ele.length() > 5)
                .count();
        assertThat(c).isEqualTo(2);
    }

    @DisplayName("Implement a function that takes a list of integers as input and returns a new list containing the square of each element")
    @Test
    void test2(){
        var s = List.of(1, 2, 3, 4, 5).stream()
                .map(ele -> ele * ele)
                .toList();
        assertThat(s).contains(1, 4, 9, 16, 25);
    }

    @DisplayName("Find the maximum element in a list of integers.")
    @Test
    void test3(){
        var s = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10).stream()
                .max(Comparator.naturalOrder())
                .orElse(0);
        assertThat(s).isEqualTo(10);
    }

    @DisplayName("Concatenate all the strings in a list into a single string.")
    @Test
    void test4(){
        var s = List.of("akshay", " ", "rathod").stream()
                .reduce("", (aggegratedString, currentEle) -> aggegratedString + currentEle);
        assertThat(s).isEqualTo("akshay rathod");
    }

    @DisplayName("Convert each string to uppercase and then sort them in alphabetical order.")
    @Test
    void test5(){
        var s = List.of("Zeel", "Vaibhav", "Akshit").stream()
                .map(String::toUpperCase)
                .sorted()
                .toList();

        assertThat(s).containsExactly("AKSHIT", "VAIBHAV", "ZEEL");
    }

    @DisplayName("Find the average of all the numbers in a list of doubles using Streams.")
    @Test
    void test6(){
        var s = List.of(5, 5).stream()
                .mapToDouble(ele -> ele)
                .summaryStatistics()
                .getAverage();
        assertThat(s).isEqualTo(5);
    }

    @DisplayName("Create a new list that contains only unique words (remove duplicates)")
    @Test
    void test7(){
        var s = List.of("Akshay", "Akshay", "Rathod").stream()
                .distinct()
                .toList();
        assertThat(s).containsExactly("Akshay", "Rathod");
    }

    @DisplayName("Check if all elements in a List satisfy a given condition using streams.")
    @Test
    void test8(){
        var s = List.of(2, 4, 6, 8).stream()
                .allMatch(ele -> ele % 2 == 0);
        assertThat(s).isTrue();
    }

    @DisplayName("Check if a list contains a specific element using streams.")
    @Test
    void test9(){
        var s = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10).stream()
                .filter(ele -> ele == 5)
                .findFirst();
        assertThat(s.isPresent()).isTrue();
    }

    @DisplayName("Find the longest string in a list using streams.")
    @Test
    void test10(){
        var s = List.of("Akshay", "Zeel", "Jim").stream()
                .sorted(Comparator.comparing(String::length).reversed())
                .limit(1)
                .findFirst()
                .orElse(null);
        assertThat(s).isEqualTo("Akshay");
    }

    @DisplayName("Remove null values from a list using streams.")
    @Test
    void test11(){
        var s = Stream.of("Akshay", "Rathod", null, null)
                .filter(Objects::nonNull)
                .toList();
        assertThat(s).containsExactly("Akshay", "Rathod");
    }

    @DisplayName("Find Second Smallest Element in a List.")
    @Test
    void test12(){
        var s = List.of(5, 1, 2, 3, 4).stream()
                .sorted()
                .skip(1)
                .limit(1)
                .findFirst()
                .orElse(-1);
        assertThat(s).isEqualTo(2);
    }

    @DisplayName("Find Intersection of Two Lists.")
    @Test
    void test13(){
        // TODO
        var s = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10).stream()
                .filter(ele -> ele % 2 == 0)
                .reduce(0, (sum, ele) -> sum + ele);
        assertThat(s).isEqualTo(30);
    }

    @DisplayName("Sum even and odd numbers in list using streams.")
    @Test
    void test14(){
        // TODO
        var s = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10).stream()
                .filter(ele -> ele % 2 == 0)
                .reduce(0, (sum, ele) -> sum + ele);
        assertThat(s).isEqualTo(30);
    }

    /**
     * You have been tasked with analyzing a dataset of employee information to determine the maximum salary in each department.
     * The dataset consists of a list of Employee objects, where each employee has a name, department, and salary.
     * You need to group the employees by their respective departments and identify the maximum salary within each department.
     */
    @DisplayName("GROUP BY Department and Find Max Salary")
    @Test
    void test15(){
        // TODO
        var s = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10).stream()
                .filter(ele -> ele % 2 == 0)
                .reduce(0, (sum, ele) -> sum + ele);
        assertThat(s).isEqualTo(30);
    }


}
