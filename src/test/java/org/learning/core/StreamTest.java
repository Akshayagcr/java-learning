package org.learning.core;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.learning.model.Employee;
import org.learning.util.TestDataUtil;

import java.util.*;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;
import static org.learning.util.TestDataUtil.getSonnet;

class StreamTest {

    @DisplayName("Streams")
    @Test @Disabled
    void testStreamsV2(){

        //  Stream sources
        {
            List.of(1, 2, 3).stream();   // Collection.stream()
            Map.ofEntries(Map.entry(1, 2)).entrySet().stream();
            Arrays.stream(new int[]{1, 2, 3});

            var s = "one two"; var pattern = Pattern.compile(" ");
            pattern.splitAsStream(s);

            "akshay".chars()          // IntStream
                    .mapToObj(c -> (char)c); // int to Character

            Stream.empty();
            Stream.of(1, 2, 3);
            Stream.generate(() -> 1);  // Generates infinite stream
            Stream.iterate(0, i -> ++i); // Generates infinite stream
            Stream.iterate(0, i -> i <  5, i -> i + 1);
        }

        //  Primitive stream(IntStream, LongStream, DoubleStream) sources
        {
            IntStream.empty();
            IntStream.of(1, 2, 3);
            IntStream.iterate(0, i -> i + 1); // Infinite sequence
            IntStream.iterate(1, i -> i < 4, i -> i + 1);
            IntStream.generate(() -> 1); // Infinite sequence

            // range(), rangeClosed() useful when iterating over index of array/Collection
            IntStream.range(1, 4);      // end excluded
            IntStream.rangeClosed(1, 4);// end included
        }

        // Intermediate operations
        {
            Stream.of(3, 2, 2, 1).filter(i -> i > 2);
            Stream.of(3, 2, 2, 1).takeWhile(ele -> ele >= 3); // Takes the longest prefix that satisfies the predicate. Once false it stops immediately.
            Stream.of(3, 2, 2, 1).dropWhile(i -> i > 2); // Drops the longest prefix that satisfies the predicate. Once false passes all element irrespective of predicate.

            Stream.of(3, 2, 2, 1).map(i -> i * 2);

            Stream.of(3, 2, 2, 1).sorted();
            Stream.of(3, 2, 2, 1).sorted(Comparator.reverseOrder());
            Stream.of(3, 2, 2, 1).distinct();

            var listOfList = new ArrayList<List<Integer>>();
            listOfList.add(List.of(1, 2));
            listOfList.add(List.of(3, 4));

            /*
                flatMap : converts one input element in empty, one or more element.
                empty, one or more element is represented by stream
             */
            listOfList.stream()
                    .flatMap(list -> list.stream());

            /*
                multiMap : is similar to flat map in sense that it converts a single element in multiple element.
                It can be used with a method that throws checked exception i.e. if there is no exception we pass elements to downstream.
                and if there is an exception we just skip.
             */
            listOfList.stream()
                            .<Integer>mapMulti((integerList, downStream) -> {
                                for(var ele : integerList){
                                    downStream.accept(ele);
                                    downStream.accept(ele * 10);
                                }
                            });

            Stream.of(3, 2, 2, 1).skip(2);
            Stream.of(3, 2, 2, 1).limit(2);
            Stream.of(3, 2, 2, 1).peek(System.out::println); // peek(action) - only to be used for debugging purpose
        }

        // Terminal operations
        {
            Stream.of(1, 2, 3).count();
            Stream.of(1, 2, 3).findFirst();
            Stream.of(1, 2, 3).findAny();
            Stream.of(1, 2, 3).anyMatch(i -> i == 3);
            Stream.of(1, 2, 3).allMatch(i -> i <= 3);
            Stream.of(1, 2, 3).noneMatch(i -> i > 3);

            Stream.of(1, 2, 3).max(Comparator.naturalOrder());
            Stream.of(1, 2, 3).min(Comparator.naturalOrder());

            Stream.of(1, 2, 3).reduce(0, (total, ele) -> total + ele);
            Stream.of(1, 2, 3).toList();
            Stream.of(1, 2, 3).toArray();
            Stream.of(1, 2, 3).forEach(System.out::println);
        }

        // Primitive stream operations
        {
            IntStream.of(1, 2, 3).mapToObj(i -> TestDataUtil.getEmployees().get(i)); // int to any custom object
            IntStream.of(1, 2, 3).boxed();  // primitive to wrapper
            Stream.of(1, 2, 3).mapToInt(Integer::intValue); // mapToLong(), mapToDouble()

            var summaryStatistics = IntStream.of(1, 2, 3).summaryStatistics();
            summaryStatistics.getAverage();
            summaryStatistics.getCount();
            summaryStatistics.getMax();
            summaryStatistics.getMin();
            summaryStatistics.getSum();
        }
    }

    /**
     * Reducing operations:-
     * Two type of reduce operation which reduces streams to specific value.
     *          1: reduce(identity, (collection/String, ele) -> collection.add(ele), Combiner for combining multiple collections)
     *          2: collect(Collector)
     */
    @DisplayName("Collectors")
    @Test @Disabled
    void testCollectors(){

        toCollection(TreeSet::new); toList(); toSet();
        toMap(Employee::name,                                   // KeyMapper
                Function.identity(),                            // ValueMapper
                (oldValue, newValue) -> newValue, // MergeFunction
                HashMap::new);                                  // MapTypeSupplier

        counting();
        summingInt(Employee::salary); // summingLong(mapper), summingDouble()
        averagingInt(Employee::salary); // averagingInt(mapper), averagingDouble(mapper)

        joining(); joining(","); joining(",", "prefix-", "-suffix");

        // mapping, filtering, flatMapping collectors can only be used as down stream collectors.
        mapping(Employee::salary, summingInt(s -> s));
        filtering((Employee emp) -> emp.salary() > 10_000, summingInt(Employee::salary));
        // flatMapping(mapper, downStreamCollector);
        
        groupingBy(Employee::department); groupingBy(Employee::department, toSet()); groupingBy(Employee::department, HashMap::new, toSet());
        partitioningBy((Employee emp) -> emp.salary() > 50_000); partitioningBy((Employee emp) -> emp.salary() > 50_000, counting());
        
        maxBy(Comparator.naturalOrder());
        minBy(Comparator.naturalOrder());
    }

    @DisplayName("Creating frequency map & inverting it")
    @Test
    void testCreateFrequencyMap(){
        var pattern = Pattern.compile(("[ ,':\\-]+"));

        var listOfWords = getSonnet().stream()
                .flatMap(pattern::splitAsStream)
                .toList();

        /*
            Frequency map
            key : word, value : frequency of word
         */
        var frequencyMap = listOfWords.stream()
                .collect(groupingBy(Function.identity(), counting()));

        /*
            Invert frequency map i.e. for each count get list of associated words
            key : count, value : List of words with count equal to key
         */
        var invertedFrequencyMap = frequencyMap.entrySet().stream()
                .collect(groupingBy(
                        Map.Entry::getValue,
                        mapping(Map.Entry::getKey,
                                toList()
                        )));

        /*
            List of all words with maximum frequency
         */
        var maximumFrequencyWords = invertedFrequencyMap.entrySet().stream()
                .max(Comparator.comparing(Map.Entry::getKey))
                .map(Map.Entry::getValue)
                .orElse(Collections.emptyList());

        System.out.println(maximumFrequencyWords);
    }
}
