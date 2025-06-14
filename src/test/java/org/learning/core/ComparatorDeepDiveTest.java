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
 *  We cannot use below in comparator as it gives wrong result for large values due to integer overflow.
 * (a, b) -> a - b (Lambda implementation for compare(obj1, obj2) method of comparator)
 * Also some math function with input double or float number return NaN value when computation is not possible.
 * So when writing comparator for double and float we also need to take care of that, which make writing comparator difficult.
 * **** So always use compare method present on wrapper classes or Comparators combinator methods
 * Integer::compare, Comparator.naturalOrder(), etc
 *
 * When we want to sort according to natural order we can pass null in place of comparator and what it means that now the sort method
 * will use Comparable interface compareTo() method implement by the value class itself for sorting.
 *
 * ************ Imp
 *
 * Eg : If we have a person object with firstName, lastName fields
 * Then
 *  1. Comparator.comparing(Person::getFirstName) is equivalent to Comparator.comparing(Person::getFirstName, Comparator.naturalOrder())
 *
 *  2. When we use Comparator.naturalOrder() it uses the compareTo(otherObj) method of Comparable interface.
 *
 *  3. As we can pass a comparator to .comparing() method, it allows following
 *              Comparator.comparing(Person::getFirstName, Comparator.comparing(String::length))
 *
 *  4. To deal with null firstName we use
 *              Comparator.comparing(Person::getFirstName, Comparator.nullsLast(Comparator.naturalOrder()))
 *
 *  5. To deal with null person object and null firstname
 *              Comparator.nullsLast(
 *                                          Comparator.comparing(Person::getFirstName,Comparator.nullsLast(Comparator.naturalOrder()))
 *                                  )
 *
 *
 * Comparator methods :
 *
 * 1: Comparator.naturalOrder(), Comparator.reverseOrder()
 *
 * 2: Comparator.comparing(keyExtractor), Comparator.comparing(keyExtractor, keyComparator),
 *      Comparator.comparingInt(keyExtractor), Comparator.comparingDouble(keyExtractor), Comparator.comparingLong(keyExtractor)
 *
 * 3: Method for chaining :-
 *      .thenComparing(keyExtractor), .thenComparing(keyExtractor, keyComparator),
 *      .thenComparingDouble(keyExtractor), .thenComparingInt(keyExtractor), .thenComparingLong(keyExtractor), .reversed()
 *
 * 4: nullsFirst(comparator), nullsLast(comparator)
 *      Eg :-
 *              Comparator.comparing(keyExtractor, nullsFirst(naturalOrder()))
 *              Comparator.comparing(keyExtractor, nullsLast(naturalOrder()))
 *
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
class ComparatorDeepDiveTest {

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
